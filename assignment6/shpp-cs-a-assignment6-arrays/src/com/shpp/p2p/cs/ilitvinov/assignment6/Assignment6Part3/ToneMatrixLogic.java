package com.shpp.p2p.cs.ilitvinov.assignment6.Assignment6Part3;
public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];

        for (int i = 0; i < toneMatrix.length; i++) {

            /*Checking if cell of ToneMatrix is active*/
            if (toneMatrix[i][column]) {

                /*Get sum of active samples
                 * [i] - row number of the sample
                 * [j] - values of intensity*/
                for (int j = 0; j < samples[i].length; j++) {
                    result[j] += samples[i][j];
                }
            }
        }

        normalizeAccord(result);
        return result;
    }

    private static void normalizeAccord(double[] accord){
        double maxIntensity = 1; // max intensity value

        //Find maxIntensity in accord
        for (double tone:accord) {
            if (Math.abs(tone)>maxIntensity){
                maxIntensity=Math.abs(tone);
            }
        }

        //Normalizing accord
        if (maxIntensity>1){

            for (int i = 0; i < accord.length; i++) {
                accord[i]/=maxIntensity;
            }
        }
    }

}
