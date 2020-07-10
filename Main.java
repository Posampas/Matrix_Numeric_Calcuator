package processor;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    static{
        Locale.setDefault(Locale.US);
    }

    enum State {menu, loadingData, Calcualting, printingResults, Terminated}

    enum CalcuatingType {addig, multiplingByConstans, matrxBymatrixMultiplication, NO_CALCUATIONS, TRANSOPSE, DETERMINANT, INVERSE}

    enum TransposeType {MAIN_DIAGONAL, SIDE_DIAGONAL, VETICAL_LINE, HORIZONTAL_LINE}



    static State programState = State.menu;
    static CalcuatingType calcType;
    static TransposeType transposeType;
    static int userInput = 0;

    static Matrix A;
    static Matrix B;
    static Matrix C;
    static double constans;

    public static void main(String[] args) {

        while (true) {

            if (programState == State.menu) {
                System.out.println("1. Add matrices\n" +
                        "2. Multiply matrix to a constant\n" +
                        "3. Multiply matrices\n" +
                        "4. Transpose matrix\n" +
                        "5. Calculate a determinant\n" +
                        "6. Inverse matrix\n" +
                        "0. Exit\n");
                System.out.print("Your choice: ");
                userInput = GetData.getUserInput();

                switch (userInput) {
                    case 0:
                        programState = State.Terminated;
                        break;
                    case 1:
                        calcType = CalcuatingType.addig;
                        programState = State.loadingData;
                        break;
                    case 2:
                        calcType = CalcuatingType.multiplingByConstans;
                        programState = State.loadingData;
                        break;
                    case 3:
                        calcType = CalcuatingType.matrxBymatrixMultiplication;
                        programState = State.loadingData;
                        break;
                    case 4:
                        calcType = CalcuatingType.TRANSOPSE;
                        System.out.println("1. Main diagonal\n" +
                                "2. Side diagonal\n" +
                                "3. Vertical line\n" +
                                "4. Horizontal line\n");
                        userInput = GetData.getUserInput();
                        switch (userInput) {
                            case 1:
                                transposeType = TransposeType.MAIN_DIAGONAL;
                                break;
                            case 2:
                                transposeType = TransposeType.SIDE_DIAGONAL;
                                break;
                            case 3:
                                transposeType = TransposeType.VETICAL_LINE;
                                break;
                            case 4:
                                transposeType = TransposeType.HORIZONTAL_LINE;
                                break;
                            default:
                                System.out.println("ERROR");

                        }
                        programState = State.loadingData;
                        break;
                    case 5:
                        calcType = CalcuatingType.DETERMINANT;
                        programState = State.loadingData;
                        break;
                    case 6:
                        calcType = CalcuatingType.INVERSE;
                        programState = State.loadingData;
                        break;
                    default:
                        System.out.println("ERROR");
                }

            } else if (programState == State.loadingData) {
                if (calcType == CalcuatingType.matrxBymatrixMultiplication) {
                    A = GetData.getMatix();
                    B = GetData.getMatix();

                    programState = State.Calcualting;
                } else if (calcType == CalcuatingType.addig) {
                    A = GetData.getMatix();
                    B = GetData.getMatix();

                    programState = State.Calcualting;
                } else if (calcType == CalcuatingType.multiplingByConstans) {

                    A = GetData.getMatix();
                    System.out.println("Enter Constans");
                    constans = GetData.getConstant();

                    programState = State.Calcualting;
                } else if (calcType == CalcuatingType.TRANSOPSE) {
                    A = GetData.getMatix();
                    programState = State.Calcualting;

                }else if (calcType == CalcuatingType.DETERMINANT
                            || calcType == CalcuatingType.INVERSE) {
                    A = GetData.getMatix();
                    programState = State.Calcualting;
                }


            } else if (programState == State.Calcualting) {
                if (calcType == CalcuatingType.matrxBymatrixMultiplication) {

                    C = Matrix.multipyMatrices(A, B);
                    programState = State.printingResults;

                }

                if (calcType == CalcuatingType.multiplingByConstans) {
                    System.out.println("Calculating multiplication");
                    C = A.mulitpyByNumber(constans);
                }

                if (calcType == CalcuatingType.addig) {
                    System.out.println("Calcuating addition");
                    C = Matrix.add(A, B);

                } else if (calcType == CalcuatingType.TRANSOPSE) {
                    C = A.transpose(transposeType);
                }else if (calcType == CalcuatingType.DETERMINANT){
                     constans = A.determinant();

                } else if (calcType == CalcuatingType.INVERSE){
                    C = A.inverse();
                }


                programState = State.printingResults;
            }

            if (programState == State.printingResults) {


                if (calcType == CalcuatingType.matrxBymatrixMultiplication) {
                    if (C == null) {
                        System.out.println("ERROR");
                    } else {
                        System.out.println(C.toString());
                    }
                } else if (calcType == CalcuatingType.addig ||
                            calcType == CalcuatingType.INVERSE) {
                    if (C == null) {
                        System.out.println("ERROR");
                    }
                    System.out.println(C);
                } else if (calcType == CalcuatingType.multiplingByConstans
                        || calcType == CalcuatingType.TRANSOPSE) {
                    System.out.println(C);
                } else if (calcType == CalcuatingType.DETERMINANT){
                    if (Double.isNaN(constans)){
                        System.out.println("ERROR");
                    }else{
                        System.out.println(constans);
                    }
                }
                programState = State.menu;
                calcType = CalcuatingType.NO_CALCUATIONS;

            }
            if (programState == State.Terminated) {
                System.exit(1);
            }
        }


    }
}
