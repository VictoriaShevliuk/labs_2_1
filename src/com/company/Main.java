package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] rags){
        //вікно для відмальовки графіків
        DrawFrame purePolyFrame;

        //Точки нашого багатокутника

        ArrayList<Float[]> polygon = inputPolygon2D();
        //Точки на перевірку
        ArrayList<Float[]> points = createPoints2D();

        System.out.println("Polygon is concave: " + isConcave(polygon));

        //Метод провірки належності точок до багатокутника
        isPointsInsidePoly(polygon, points);
        purePolyFrame = new DrawFrame("Check point", polygon, points);

    }

    //метод вводу точок багатокутника
    static ArrayList<Float[]> inputPolygon2D() {
        System.out.println("Input polygon:");
        ArrayList<Float[]> points = new ArrayList<>();

        //Вводимо початкову точку
        System.out.println("Input start point:");
        Float[] startPoint = inputPoint2D();
        Float[] lastPoint = startPoint;
        //Продовжуємо ввід доки поточна точка не відповідатиме початковій
        do {
            points.add(lastPoint);
            lastPoint = inputPoint2D();
        } while(!comparePoints(startPoint, lastPoint));
        System.out.println("Polygon has been created.");

        return points;
    }

    //Вводимо точки на перевірку
    static ArrayList<Float[]> createPoints2D(){
        System.out.println("Input points:");
        ArrayList<Float[]> points = new ArrayList<>();
        try {
            String ans = "y";
            while (ans.charAt(0) == 'y') {
                points.add(inputPoint2D());
                System.out.println("Next point?: (y/enter)");
                ans = input.readLine();

                if (ans.length() == 0) {
                    break;
                } else if (ans.charAt(0) != 'y') {
                    System.out.println("There is no such variant!");
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return points;
    }

    static Float[] inputPoint2D() {
        Float[] point2D = new Float[2];
        while (true) {
            try {
                System.out.println("Input X:");
                point2D[0] = Float.parseFloat(input.readLine());

                System.out.println("Input Y:");
                point2D[1] = Float.parseFloat(input.readLine());
            }
            catch (IOException ex){
                ex.printStackTrace();
                continue;
            }
            break;
        }
        return point2D;
    }

    //Перевіряємо багатокутник на увігнутість
    static boolean isConcave(ArrayList<Float[]> points){
        boolean result = false;

        float crossProdZ;
        float prevCrossProdZ = 0;

        //Порівнюємо z-координати векторного добутку точок кожного кута багатокутника.
        //якщо знаки цих векторних добутків змінюються - багатокутник увігнутий.
        for(int i = 1; i < points.size() - 1; i++){
            crossProdZ = crossProductZ(points.get(i - 1), points.get(i), points.get(i + 1));
            if(i != 1){
                if(crossProdZ / Math.abs(crossProdZ) != prevCrossProdZ / Math.abs(prevCrossProdZ)){
                    result = true;
                    break;
                }
            }
            prevCrossProdZ = crossProdZ;
        }
        return result;
    }

    //Обчислюємо z-координату векторного добутку векторів АВ ВС
    static float crossProductZ(Float[] pntA, Float[] pntB, Float[] pntC){
        return (pntB[0] - pntA[0]) * (pntC[1] - pntB[1]) - (pntB[1] - pntA[1]) * (pntC[0] - pntB[0]);
    }

    //Обчислюємо скалярний добуток
    static float dotProduct(Float[] vector1, Float[] vector2){
        return vector1[0] * vector2[0] + vector1[1] * vector2[1];
    }

    //Порівнюємо дві точки
    static boolean comparePoints(Float[] pntA, Float[] pntB){
        return pntA[0].equals(pntB[0]) && pntA[1].equals(pntB[1]);
    }

    //Перевіряємо, чи лежить точка всередині багатокутника
    static void isPointsInsidePoly(ArrayList<Float[]> polyPoints, ArrayList<Float[]> pointsForCheck){
        //перевірка на увігнутість багатокутника
        if(!isConcave(polyPoints)){
            //Для усіх точок на перевірку
            for(Float[] point : pointsForCheck){
                float minLength = Float.MAX_VALUE;

                Float[][] nearestSide = new Float[2][];
                Float[] nearestPoint = null;

                //Починаємо обхід поточкам багатокутника, обираючи по 2 точки за цикл
                //Таким чином імітуємо вибір сторони
                for(int i = 1; i < polyPoints.size(); i++){
                    //Знаходимо проекцію точки на поточну сторону
                    Float[] tempProjPnt = projectionPoint(polyPoints.get(i - 1), polyPoints.get(i), point);
                    //Знаходимо відстань між точкою та її проекцією
                    float distanceToProjPnt = (float)Math.sqrt(
                            (float)Math.pow(tempProjPnt[0] - point[0], 2) +
                                    (float)Math.pow(tempProjPnt[1] - point[1], 2));
                    //Якщо ця відстань менше поточної мінімальної - запам’ятовуємо цю точку та сторону
                    if(minLength > distanceToProjPnt){
                        minLength = distanceToProjPnt;

                        nearestSide[0] = polyPoints.get(i - 1);
                        nearestSide[1] = polyPoints.get(i);

                        nearestPoint = tempProjPnt;
                    }
                }

                float dotProduct = 0;
                //вектор між точками на найближчій стороні
                Float[] fromPntToNearestVector = {
                        nearestPoint[0] - point[0],
                        nearestPoint[1] - point[1]
                };

                //Якщо найближча точка співпадає з точкою А найближчого відрізка
                boolean pntAIsNearest = comparePoints(nearestSide[0], nearestPoint);
                //Якщо найближча точка співпадає з точкою  В найближчого відрізка
                boolean pntBIsNearest = comparePoints(nearestSide[1], nearestPoint);
                if(pntAIsNearest || pntBIsNearest){
                    Float[] otherSidePnt = null;
                    Float[][] otherSide = new Float[2][];

                    int idxOfPnt = 0;
                    //Якщо найближча точка - це точка А
                    if(pntAIsNearest){
                        //Беремо індекс на 1 менше, ніж індекс точки А
                        idxOfPnt = polyPoints.indexOf(nearestPoint) - 1;
                        //Точку А встановлюємо, як точку В для попереднього відрізку
                        otherSide[1] = nearestPoint;
                    }
                    //якщо найближчка точка - це точка В
                    else{
                        //Беремо індекс на 1 більше, ніж індекс точки В
                        idxOfPnt = polyPoints.indexOf(nearestPoint) + 1;
                        //Точку В встановлюємо, як точку А для наступного відрізка
                        otherSide[0] = nearestPoint;
                    }

                    //Коррекція випадків, коли індекс точки иходить за межі масиву
                    //Робимо "кільцевий" список
                    if(idxOfPnt < 0) {
                        idxOfPnt = polyPoints.size() - idxOfPnt;
                    }
                    else if(idxOfPnt >= polyPoints.size()) {
                        idxOfPnt -= polyPoints.size();
                    }
                    //Отримуємо точку попередньої\наступної сторони
                    otherSidePnt = polyPoints.get(idxOfPnt);

                    if(otherSide[0] == null) otherSide[0] = otherSidePnt;
                    else otherSide[1] = otherSidePnt;

                    //Обчислюємо вектор нормалі найближчої точки
                    //Вектор дорівнює сумі векторів нормалі сторін, для яких ця точка є спільною
                    Float[] pntNormalVector = {
                            (nearestSide[1][1] - nearestSide[0][1]) + (otherSide[1][1] - otherSide[0][1]),
                            (nearestSide[0][0] - nearestSide[1][0]) + (otherSide[0][0] - otherSide[1][0])
                    };
                    //Обчислюємо скалярний добуток векторів від поточної точки до найближчої точки
                    //та вектору нормалі найближчої точки
                    dotProduct = dotProduct(fromPntToNearestVector, pntNormalVector);
                }
                else {
                    //Обчислюємо вектор нормалі найближчої сторони до поточної
                    Float[] nearestSideNormalVector = {
                            nearestSide[1][1] - nearestSide[0][1],
                            nearestSide[0][0] - nearestSide[1][0]
                    };
                    //Обчислюємо скалярний добуток вектора від точки поточної до найближчої точки
                    //та вектора нормалі найближчої сторони
                    dotProduct = dotProduct(fromPntToNearestVector, nearestSideNormalVector);
                }

                if(dotProduct > 0){
                    System.out.printf("Point: [%.2f, %.2f] is inside.\n\n", point[0], point[1]);
                }
                else {
                    System.out.printf("Point: [%.2f, %.2f] is outside.\n\n", point[0], point[1]);
                }
            }
        }
        else{
            System.out.println("The polygon is not Convex!");
        }
    }

    //Знаходимо проекцію точки на відрізку АВ
    static Float[] projectionPoint(Float[] pntA, Float[] pntB, Float[] addPoint){
        //Вектор нормали прямой АВ
        Float[] normalVectorAB = { pntB[1] - pntA[1], -(pntB[0] - pntA[0])};

        //Коефіцієнти загального рівняння прямої, побудовані по додатковій точці(С)
        //та вектору нормалі прямої АВ. Перпендикулярна АВ
        float A2 = normalVectorAB[1];
        float B2 = -normalVectorAB[0];
        float C2 = normalVectorAB[0] * addPoint[1] - normalVectorAB[1] * addPoint[0];

        //Коефіцієнти загального рівняння прямої АВ
        float A = normalVectorAB[0];
        float B = normalVectorAB[1];
        float C = pntA[1] * pntB[0] - pntB[1] * pntA[0];

        float denominator = A * B2 - A2 * B;

        //Знаходимо точку перетину (проекції) двох прямих
        float y = (A2 * C - A * C2) / denominator;
        float x = -(B * y + C) / A;


        float deltaX = pntB[0] - pntA[0];
        float deltaY = pntB[1] - pntA[1];

        float deltaPntAX = pntA[0] - addPoint[0];
        float deltaPntAY = pntA[1] - addPoint[1];

        float segmentParameter = -(deltaPntAX * deltaX + deltaPntAY * deltaY) /
                ((float)Math.pow(deltaX, 2) + (float)Math.pow(deltaY, 2));

        //Визначаємо, чи лежить точка в межах відрізка АВ,
        //чи лежить за його межами

        Float[] retPnt = {x, y};

        //якщо параметр менше 0, значить точка лежить за межами точки А
        //Тому точкою проекції буде точка А
        if (segmentParameter < 0)
        {
            retPnt = pntA;
        }
        //Якщо параметр більше 1, значить точка лежить за межами точки В
        //Тому точкою проекції буде точка В
        else if (segmentParameter > 1)
        {
            retPnt = pntB;
        }
        return retPnt;
    }
}
