package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] rags){

        DrawFrame purePolyFrame, grahamPolyFrame, jarvisPolyFrame;


        ArrayList<Float[]> polygon = inputPolygon2D();

        ArrayList<Float[]> points = createPoints2D();

        System.out.println("Polygon is concave: " + isConcave(polygon));


        isPointsInsidePoly(polygon, points);
        purePolyFrame = new DrawFrame("Check point", polygon, points);

        //Методы построения выпуклого многоугольника поверх существующего невыпуклого
        //Метод Грехэма
        ArrayList<Float[]> outerPoly = grahamScan(polygon);
        grahamPolyFrame = new DrawFrame("Graham Scan", polygon, points, outerPoly);
        //Метод Джарвиса
        outerPoly = jarvisMarch(polygon);
        jarvisPolyFrame = new DrawFrame("Jarvis March", polygon, points, outerPoly);
    }


    static ArrayList<Float[]> inputPolygon2D() {
        System.out.println("Input polygon:");
        ArrayList<Float[]> points = new ArrayList<>();


        System.out.println("Input start point:");
        Float[] startPoint = inputPoint2D();
        Float[] lastPoint = startPoint;

        do {
            points.add(lastPoint);
            lastPoint = inputPoint2D();
        } while(!comparePoints(startPoint, lastPoint));
        System.out.println("Polygon has been created.");

        return points;
    }


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


    static boolean isConcave(ArrayList<Float[]> points){
        boolean result = false;

        float crossProdZ;
        float prevCrossProdZ = 0;


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


    static float crossProductZ(Float[] pntA, Float[] pntB, Float[] pntC){
        return (pntB[0] - pntA[0]) * (pntC[1] - pntB[1]) - (pntB[1] - pntA[1]) * (pntC[0] - pntB[0]);
    }


    static float dotProduct(Float[] vector1, Float[] vector2){
        return vector1[0] * vector2[0] + vector1[1] * vector2[1];
    }


    static boolean comparePoints(Float[] pntA, Float[] pntB){
        return pntA[0].equals(pntB[0]) && pntA[1].equals(pntB[1]);
    }


    static void isPointsInsidePoly(ArrayList<Float[]> polyPoints, ArrayList<Float[]> pointsForCheck){

        if(isConcave(polyPoints)){

            for(Float[] point : pointsForCheck){
                float minLength = Float.MAX_VALUE;

                Float[][] nearestSide = new Float[2][];
                Float[] nearestPoint = null;


                for(int i = 1; i < polyPoints.size(); i++){

                    Float[] tempProjPnt = projectionPoint(polyPoints.get(i - 1), polyPoints.get(i), point);

                    float distanceToProjPnt = (float)Math.sqrt(
                            (float)Math.pow(tempProjPnt[0] - point[0], 2) +
                                    (float)Math.pow(tempProjPnt[1] - point[1], 2));

                    if(minLength > distanceToProjPnt){
                        minLength = distanceToProjPnt;

                        nearestSide[0] = polyPoints.get(i - 1);
                        nearestSide[1] = polyPoints.get(i);

                        nearestPoint = tempProjPnt;
                    }
                }

                float dotProduct = 0;

                Float[] fromPntToNearestVector = {
                        nearestPoint[0] - point[0],
                        nearestPoint[1] - point[1]
                };


                boolean pntAIsNearest = comparePoints(nearestSide[0], nearestPoint);

                boolean pntBIsNearest = comparePoints(nearestSide[1], nearestPoint);
                if(pntAIsNearest || pntBIsNearest){
                    Float[] otherSidePnt = null;
                    Float[][] otherSide = new Float[2][];

                    int idxOfPnt = 0;

                    if(pntAIsNearest){

                        idxOfPnt = polyPoints.indexOf(nearestPoint) - 1;

                        otherSide[1] = nearestPoint;
                    }

                    else{

                        idxOfPnt = polyPoints.indexOf(nearestPoint) + 1;

                        otherSide[0] = nearestPoint;
                    }


                    if(idxOfPnt < 0) {
                        idxOfPnt = polyPoints.size() - idxOfPnt;
                    }
                    else if(idxOfPnt >= polyPoints.size()) {
                        idxOfPnt -= polyPoints.size();
                    }

                    otherSidePnt = polyPoints.get(idxOfPnt);

                    if(otherSide[0] == null) otherSide[0] = otherSidePnt;
                    else otherSide[1] = otherSidePnt;

                    Float[] pntNormalVector = {
                            (nearestSide[1][1] - nearestSide[0][1]) + (otherSide[1][1] - otherSide[0][1]),
                            (nearestSide[0][0] - nearestSide[1][0]) + (otherSide[0][0] - otherSide[1][0])
                    };

                    dotProduct = dotProduct(fromPntToNearestVector, pntNormalVector);
                }
                else {

                    Float[] nearestSideNormalVector = {
                            nearestSide[1][1] - nearestSide[0][1],
                            nearestSide[0][0] - nearestSide[1][0]
                    };

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
            System.out.println("The polygon is not Concave!");
        }
    }

    //Алгоритм Грехэма
    static ArrayList<Float[]> grahamScan(ArrayList<Float[]> polyPoints){
        ArrayList<Float[]> polyPointsCopy = new ArrayList<>(polyPoints);
        //Минимум от трёх точек
        if (polyPointsCopy.size() < 3) {
            return null;
        }
        //Самую левую точку переносим в начало списка
        for(int i = 1; i < polyPointsCopy.size(); i++){
            if(polyPointsCopy.get(0)[0] > polyPointsCopy.get(i)[0]){
                Float[] tempPoint = polyPointsCopy.get(0);
                polyPointsCopy.set(0, polyPointsCopy.get(i));
                polyPointsCopy.set(i, tempPoint);
            }
        }
        //Сортировка вставками. Сортируем точки. Самые правые точки, относительно начальной,
        //окажутся в самом начале списка
        pointsInsertionSort(polyPointsCopy);
        //Список точек в правильном порядке построения многоугольника
        ArrayList<Float[]> rightSequence = new ArrayList<>();
        //Сразу закидываем первые 2 точки, начальную и следующую
        rightSequence.add(polyPointsCopy.get(0));
        rightSequence.add(polyPointsCopy.get(1));

        //Проходим весь список, начиная с третьей точки
        for(int i = 2;  i < polyPointsCopy.size(); i++){
            //Пока z-координата векторного произведения предпоследней, последней и текущей точек
            //меньше нуля, что значит, что текущая точка находится справа, удаляем последнюю точку
            //из списка
            while(crossProductZ(rightSequence.get(rightSequence.size() - 2),
                    rightSequence.get(rightSequence.size() - 1),
                    polyPointsCopy.get(i)) < 0){
                rightSequence.remove(rightSequence.size() - 1);
            }
            //Точку, которая слева - добавляем в список правильной последовательности
            rightSequence.add(polyPointsCopy.get(i));
        }
        return rightSequence;
    }

    //Алгоритм Джарвиса
    static ArrayList<Float[]> jarvisMarch(ArrayList<Float[]> polyPoints){
        ArrayList<Float[]> polyPointsCopy = new ArrayList<>(polyPoints);
        //Самую левую точку переносим в начало списка
        for(int i = 1; i < polyPointsCopy.size(); i++){
            if(polyPointsCopy.get(0)[0] > polyPointsCopy.get(i)[0]){
                Float[] tempPoint = polyPointsCopy.get(0);
                polyPointsCopy.set(0, polyPointsCopy.get(i));
                polyPointsCopy.set(i, tempPoint);
            }
        }
        //Список точек в правильном порядке построения многоугольника
        ArrayList<Float[]> rightOrder = new ArrayList<>();
        //Переносим начальную точку в конце списка
        rightOrder.add(polyPointsCopy.get(0));
        polyPointsCopy.remove(0);
        polyPointsCopy.add(rightOrder.get(0));

        //Бесконечный цикл
        while(true){
            //Индекс "правой" точки
            int pointIdx = 0;
            //Проходим весь список точек
            for(int i = 0; i < polyPointsCopy.size(); i++){
                //Если мешнье нуля - текущая точка расположена "справа"
                if(crossProductZ(rightOrder.get(rightOrder.size() - 1),
                        polyPointsCopy.get(pointIdx),
                        polyPointsCopy.get(i)) < 0){
                    // Запоминаем индекс последней правой точки,
                    // для того, чтоб игнорировать предыдущую правую
                    pointIdx = i;
                }
            }
            //Сравниваем начальную точку и "Правую". Если точки не совпали - запоминаем эту правую точку
            //и удаляем её из списка (таким образом, 1-я точка станет 0-й, и т.д.)
            if(!comparePoints(polyPointsCopy.get(pointIdx), rightOrder.get(0))){
                rightOrder.add(polyPointsCopy.get(pointIdx));
                polyPointsCopy.remove(pointIdx);
            }
            //Если точки совпали - выходим из цикла и завершаем создание оболочки
            else break;
        }
        return rightOrder;
    }

    //Сортировка вставками. Те точки, что справа от вектора АВ - меньше чем те, что слева
    static void pointsInsertionSort(ArrayList<Float[]> points){
        for(int i = 2; i < points.size(); i++){
            int j = i;
            while (j > 1 && crossProductZ(points.get(0), points.get(j - 1), points.get(j)) < 0){
                Float[] tempPoint = points.get(j);
                points.set(j, points.get(j - 1));
                points.set(j - 1, tempPoint);
                j -= 1;
            }
        }
    }


    static Float[] projectionPoint(Float[] pntA, Float[] pntB, Float[] addPoint){

        Float[] normalVectorAB = { pntB[1] - pntA[1], -(pntB[0] - pntA[0])};


        float A2 = normalVectorAB[1];
        float B2 = -normalVectorAB[0];
        float C2 = normalVectorAB[0] * addPoint[1] - normalVectorAB[1] * addPoint[0];


        float A = normalVectorAB[0];
        float B = normalVectorAB[1];
        float C = pntA[1] * pntB[0] - pntB[1] * pntA[0];

        float denominator = A * B2 - A2 * B;


        float y = (A2 * C - A * C2) / denominator;
        float x = -(B * y + C) / A;


        float deltaX = pntB[0] - pntA[0];
        float deltaY = pntB[1] - pntA[1];

        float deltaPntAX = pntA[0] - addPoint[0];
        float deltaPntAY = pntA[1] - addPoint[1];

        float segmentParameter = -(deltaPntAX * deltaX + deltaPntAY * deltaY) /
                ((float)Math.pow(deltaX, 2) + (float)Math.pow(deltaY, 2));


        Float[] retPnt = {x, y};


        if (segmentParameter < 0)
        {
            retPnt = pntA;
        }

        else if (segmentParameter > 1)
        {
            retPnt = pntB;
        }
        return retPnt;
    }
}
