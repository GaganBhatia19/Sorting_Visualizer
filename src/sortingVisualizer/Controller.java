/*
 *       Sorting Visualizer by Gagan Bhatia
 *
 *       Github profile: GaganBhatia19
 * */

package sortingVisualizer;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public final class Controller implements Initializable {
    @FXML
    private BorderPane displayBorderPane;
    @FXML
    private Slider arraySizeSlider, delaySlider;
    @FXML
    private Label statusLabel, arraySizeSliderLabel, delaySliderLabel,
            randomizeBtn, bubbleSortBtn, insertionSortBtn,
            selectionSortBtn, shellSortBtn, quickSortBtn,
            mergeSortBtn, aboutBtn, sortingAlgorithmLabel;

    private int TOTAL_NUMBER_OF_BARS;
    private int DELAY_TIME;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series series;
    private BarChart barChart;
    private String CURRENT_SORTING;
    private final String BACKGROUND_THEME = "-fx-background-color: #0C2633;",
            MAIN_THEME = "-fx-background-color: #00D8FA;",
            DEFAULT_TEXT_COLOR = "-fx-text-fill: #00D8FA;",
            TEXT_COLOR_BRIGHT_GREEN = "-fx-text-fill: #39FF14;",
            SELECTED_BARS_COLOR_FILL = "-fx-background-color: #FFAAAA;",
            SELECTED_BARS_BORDER_COLOR = "-fx-border-color: #FF7F7F;",
            CURRENT_INDEX_COLOR = "-fx-background-color: #39FF14;",
            BARS_DISABLE_COLOR = "-fx-background-color: #808080";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();

        TOTAL_NUMBER_OF_BARS = (int) arraySizeSlider.getValue();
        arraySizeSliderLabel.setText(Integer.toString(TOTAL_NUMBER_OF_BARS));
        arraySizeSlider.valueProperty().addListener(e -> {
            TOTAL_NUMBER_OF_BARS = (int) arraySizeSlider.getValue();
            arraySizeSliderLabel.setText(Integer.toString(TOTAL_NUMBER_OF_BARS));
            randomize();
        });
        randomize();
        randomizeBtn.setOnMouseClicked(e -> randomize());

        DELAY_TIME = (int) delaySlider.getValue();
        delaySliderLabel.setText((int) delaySlider.getValue() + " ms");
        delaySlider.valueProperty().addListener(e -> {
            DELAY_TIME = (int) delaySlider.getValue();
            delaySliderLabel.setText((int) delaySlider.getValue() + " ms");
        });

        bubbleSortBtn.setOnMouseClicked(e -> bubbleSort());
        insertionSortBtn.setOnMouseClicked(e -> insertionSort());
        selectionSortBtn.setOnMouseClicked(e -> selectionSort());
        shellSortBtn.setOnMouseClicked(e -> shellSort());
        quickSortBtn.setOnMouseClicked(e -> quickSort());
        mergeSortBtn.setOnMouseClicked(e -> mergeSort());

        aboutBtn.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Application & Developer");
            alert.setHeaderText("Developed by Gagan Bhatia");
            alert.setContentText("Visualize Bubble Sort Algorithm, Insertion Sort Algorithm, Selection Sort Algorithm, Shell Sort, Quick Sort Algorithm & Merge Sort Algorithm and see how these work.\n\nTip: If size of array is larger make sure that you decrease the delay time!");
            alert.show();
        });


        randomizeBtn.setTooltip(new Tooltip("Reset the array and Randomize with random values"));
        bubbleSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n)"));
        insertionSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n)"));
        selectionSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n²)"));
        shellSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n log n)"));
        quickSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\tBest Case\nTime Complexity: O(n²)\tTime Complexity: O(n log n)"));
        mergeSortBtn.setTooltip(new Tooltip("\tWorst case\t\t\t\tBest Case\nTime Complexity: O(n log n)\tTime Complexity: O(n log n)"));
    }

    private void delay() {
        try {
            Thread.sleep(DELAY_TIME);
        } catch (Exception e) {}
    }

    private void randomize() {
        series = new XYChart.Series();
        barChart = new BarChart(xAxis, yAxis);
        for (int i = 0; i < TOTAL_NUMBER_OF_BARS; i++) {
            series.getData().add(new XYChart.Data(Integer.toString(i), new Random().nextInt(TOTAL_NUMBER_OF_BARS) + 1));
        }
        barChart.getData().add(series);
        xAxis.setOpacity(0);
        yAxis.setOpacity(0);

        displayBorderPane.setCenter(barChart);

        for (int i = 0; i < TOTAL_NUMBER_OF_BARS; i++)
            ((XYChart.Data<Object, Object>) series.getData().get(i)).getNode().setStyle(MAIN_THEME);

        barChart.setBarGap(0);
        barChart.setCategoryGap(3);

        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setHorizontalZeroLineVisible(false);
        barChart.setVerticalZeroLineVisible(false);

        statusLabel.setText("UNSORTED");
        statusLabel.setStyle(DEFAULT_TEXT_COLOR);

        sortingAlgorithmLabel.setText("NONE");
        sortingAlgorithmLabel.setStyle(DEFAULT_TEXT_COLOR);
    }

    private void setAllDisable(boolean b) {
        bubbleSortBtn.setDisable(b);
        insertionSortBtn.setDisable(b);
        selectionSortBtn.setDisable(b);
        shellSortBtn.setDisable(b);
        quickSortBtn.setDisable(b);
        mergeSortBtn.setDisable(b);
        randomizeBtn.setDisable(b);
        arraySizeSlider.setDisable(b);
    }

    private void changeStyleEffect(int index, String style) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index)).getNode().setStyle(style);
            } catch (Exception e) {}
        });
    }

    private void changeStyleEffect(int index, String style, String borderColor) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index)).getNode().setStyle(style + borderColor);
            } catch (Exception e) {}
        });
    }

    private void changeStyleEffect(int index1, String style1, int index2, String style2) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index1)).getNode().setStyle(style1);
                ((XYChart.Data) series.getData().get(index2)).getNode().setStyle(style2);
            } catch (Exception e) {}
        });
    }

    private void changeStyleEffect(int index1, String style1, String borderColor1, int index2, String style2, String borderColor2) {
        Platform.runLater(() -> {
            try {
                ((XYChart.Data) series.getData().get(index1)).getNode().setStyle(style1 + borderColor1);
                ((XYChart.Data) series.getData().get(index2)).getNode().setStyle(style2 + borderColor2);
            } catch (Exception e) {}
        });
    }

    private void barsDisableEffect() {
        for (int i = 0; i < TOTAL_NUMBER_OF_BARS; i++) {
            ((XYChart.Data) series.getData().get(i)).getNode().setStyle(BARS_DISABLE_COLOR);
        }
    }

    private void barsDisableEffect(int i, int j) {
        Platform.runLater(() -> {
            for (int x = i; x < j; x++) {
                ((XYChart.Data) series.getData().get(x)).getNode().setStyle(BARS_DISABLE_COLOR);
            }
        });
    }

    private void isArraySorted(boolean b) {
        Platform.runLater(() -> {
            if (b) {
                statusLabel.setText("SORTED");
                statusLabel.setStyle(TEXT_COLOR_BRIGHT_GREEN);
            } else {
                statusLabel.setText("SORTING...");
                statusLabel.setStyle(DEFAULT_TEXT_COLOR);
            }
        });
    }

    private void selectSortingAlgorithm() {
        Platform.runLater(() -> {
                    sortingAlgorithmLabel.setText(CURRENT_SORTING);
                    sortingAlgorithmLabel.setStyle(TEXT_COLOR_BRIGHT_GREEN);
                }
        );
    }

    // BUBBLE SORT
    private void bubbleSort() {
        setAllDisable(true);
        CURRENT_SORTING = "BUBBLE SORT";
        selectSortingAlgorithm();
        isArraySorted(false);
        new Thread(() -> {
            barsDisableEffect();
            boolean flag;
            for (int i = 0; i < TOTAL_NUMBER_OF_BARS - 1; i++) {
                flag = false;
                for (int j = 0; j < TOTAL_NUMBER_OF_BARS - i - 1; j++) {
                    changeStyleEffect(j, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, j + 1, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                    delay();
                    if ((int) ((XYChart.Data) series.getData().get(j)).getYValue() > (int) ((XYChart.Data) series.getData().get(j + 1)).getYValue()) {
                        CountDownLatch latch = new CountDownLatch(1);
                        int finalJ = j;
                        Platform.runLater(() -> {
                            ParallelTransition pt = swapAnimation(finalJ, finalJ + 1);
                            pt.setOnFinished(e -> latch.countDown());
                            pt.play();
                        });
                        try {
                            latch.await();
                        } catch (Exception e) {
                        }
                        flag = true;
                    }
                    changeStyleEffect(j, MAIN_THEME, j + 1, MAIN_THEME);
                }
                if (!flag) break;
            }
            setAllDisable(false);
            isArraySorted(true);
        }).start();
    }

    // INSERTION SORT
    private void insertionSort() {
        setAllDisable(true);
        CURRENT_SORTING = "INSERTION SORT";
        selectSortingAlgorithm();
        isArraySorted(false);
        new Thread(() -> {
            barsDisableEffect();
            int i = 1, j;
            while (i < TOTAL_NUMBER_OF_BARS) {
                int x = (int) ((XYChart.Data) series.getData().get(i)).getYValue();
                j = i - 1;
                changeStyleEffect(i, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, j, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                delay();
                while (j >= 0 && (int) ((XYChart.Data) series.getData().get(j)).getYValue() > x) {
                    CountDownLatch latch = new CountDownLatch(1);
                    int finalJ = j;
                    changeStyleEffect(finalJ, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, finalJ + 1, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                    Platform.runLater(() -> {
                        ParallelTransition pt = swapAnimation(finalJ, finalJ + 1);
                        pt.setOnFinished(e -> latch.countDown());
                        pt.play();
                    });
                    try {
                        latch.await();
                    } catch (Exception e) {
                    }
                    changeStyleEffect(finalJ, MAIN_THEME, finalJ + 1, MAIN_THEME);
                    j--;
                }
                changeStyleEffect(i, MAIN_THEME, j, MAIN_THEME);
                ((XYChart.Data) series.getData().get(j + 1)).setYValue(x);
                i++;
            }
            setAllDisable(false);
            isArraySorted(true);
        }).start();
    }

    // SELECTION SORT
    private void selectionSort() {
        new Thread(() -> {
            setAllDisable(true);
            CURRENT_SORTING = "SELECTION SORT";
            selectSortingAlgorithm();
            isArraySorted(false);
            barsDisableEffect();
            int i, j, k;
            i = 0;
            while (i < TOTAL_NUMBER_OF_BARS - 1) {
                k = i;
                j = i + 1;
                int finalI = i;
                while (j < TOTAL_NUMBER_OF_BARS) {
                    int finalJ = j, finalK = k;
                    changeStyleEffect(finalI, CURRENT_INDEX_COLOR);
                    changeStyleEffect(finalJ, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, finalK, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                    delay();
                    if ((int) ((XYChart.Data) series.getData().get(j)).getYValue() < (int) ((XYChart.Data) series.getData().get(k)).getYValue()) {
                        k = j;
                    }
                    changeStyleEffect(finalJ, MAIN_THEME, finalK, MAIN_THEME);
                    j++;
                }
                int finalK = k;
                CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    ParallelTransition pt = swapAnimation(finalI, finalK);
                    pt.setOnFinished(e -> latch.countDown());
                    pt.play();
                });
                try {
                    latch.await();
                } catch (Exception e) {
                }
                changeStyleEffect(finalI, MAIN_THEME);
                i++;
            }
            setAllDisable(false);
            isArraySorted(true);
        }).start();
    }
    // SHELL SORT
    private void shellSort() {
        new Thread(() -> {
            setAllDisable(true);
            CURRENT_SORTING = "SHELL SORT";
            selectSortingAlgorithm();
            isArraySorted(false);
            barsDisableEffect();
            int gap = TOTAL_NUMBER_OF_BARS / 2;
            int i, j, x;
            while (gap >= 1) {
                i = gap;
                // insertion sort procedure
                while (i < TOTAL_NUMBER_OF_BARS) {
                    j = i - gap;
                    x = (int) ((XYChart.Data) series.getData().get(i)).getYValue();
                    changeStyleEffect(i, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, j, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                    delay();
                    while (j >= 0 && (int) ((XYChart.Data) series.getData().get(j)).getYValue() > x) {
                        CountDownLatch latch = new CountDownLatch(1);
                        int finalJ = j, finalGap = gap;
                        changeStyleEffect(finalJ, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, finalJ + finalGap, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                        Platform.runLater(() -> {
                            ParallelTransition pt = swapAnimation(finalJ, finalJ + finalGap);
                            pt.setOnFinished(e -> latch.countDown());
                            pt.play();
                        });
                        try {
                            latch.await();
                        } catch (Exception e) {
                        }
                        changeStyleEffect(finalJ, MAIN_THEME, finalJ + finalGap, MAIN_THEME);
                        j -= gap;
                    }
                    changeStyleEffect(i, MAIN_THEME, j, MAIN_THEME);
                    ((XYChart.Data) series.getData().get(j + gap)).setYValue(x);
                    i++;
                }
                gap /= 2;
            }
            setAllDisable(false);
            isArraySorted(true);
        }).start();
    }

    // QUICK SORT
    private void quickSort() {
        new Thread(() -> {
            setAllDisable(true);
            CURRENT_SORTING = "QUICK SORT";
            selectSortingAlgorithm();
            isArraySorted(false);
            quickSortRec(0, TOTAL_NUMBER_OF_BARS - 1);
            setAllDisable(false);
            isArraySorted(true);
        }).start();
    }
    private void quickSortRec(int startIdx, int endIdx) {
        int idx = partition(startIdx, endIdx);

        if (startIdx < idx - 1) {
            quickSortRec(startIdx, idx - 1);
        }

        if (endIdx > idx) {
            quickSortRec(idx, endIdx);
        }
    }
    private int partition(int left, int right) {
        barsDisableEffect(left, right);
        int pivot = (int) ((XYChart.Data) series.getData().get(left)).getYValue();
        Node pivotNode = ((XYChart.Data) series.getData().get(left)).getNode();
        Platform.runLater(() -> pivotNode.setStyle(CURRENT_INDEX_COLOR));
        delay();
        while (left <= right) {
            while ((int) ((XYChart.Data) (series.getData().get(left))).getYValue() < pivot) {
                int finalLeft = left;
                changeStyleEffect(finalLeft, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                delay();
                left++;
            }
            while ((int) ((XYChart.Data) (series.getData().get(right))).getYValue() > pivot) {
                int finalRight = right;
                changeStyleEffect(finalRight, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
                delay();
                right--;
            }
            changeStyleEffect(left, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, right, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
            delay();
            if (left <= right) {
                int finalLeft = left, finalRight = right;
                CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    ParallelTransition pt = swapAnimation(finalLeft, finalRight);
                    pt.setOnFinished(e -> latch.countDown());
                    pt.play();
                });
                try {
                    latch.await();
                } catch (Exception e) {
                }

                changeStyleEffect(left, MAIN_THEME, right, MAIN_THEME);
                left++;
                right--;
            }
            changeStyleEffect(left, MAIN_THEME, right, MAIN_THEME);
        }
        return left;
    }

    // MERGE SORT
    private void mergeSort() {
        new Thread(() -> {
            setAllDisable(true);
            CURRENT_SORTING = "MERGE SORT";
            selectSortingAlgorithm();
            isArraySorted(false);
            mergeSortRec(0, TOTAL_NUMBER_OF_BARS - 1);
            setAllDisable(false);
            isArraySorted(true);
        }).start();
    }

    private void mergeSortRec(int l, int h) {
        int mid;
        if (l < h) {
            mid = (l + h) / 2;

            mergeSortRec(l, mid);
            mergeSortRec(mid + 1, h);

            mergeOperation(l, mid, h);
        }
    }
    private void mergeOperation(int low, int mid, int high) {
        int i = low, j = mid + 1, k = low;

        // Auxiliary Series / Array
        XYChart.Series copySeries = new XYChart.Series();
        for (int x = 0; x < TOTAL_NUMBER_OF_BARS; x++) {
            copySeries.getData().add(new XYChart.Data(Integer.toString(x), (int) ((XYChart.Data) series.getData().get(i)).getYValue()));
        }

        barsDisableEffect(i, j);
        while (i <= mid && j <= high) {
            changeStyleEffect(i, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR, j, SELECTED_BARS_COLOR_FILL, SELECTED_BARS_BORDER_COLOR);
            delay();
            if ((int) ((XYChart.Data) series.getData().get(i)).getYValue() < (int) ((XYChart.Data) series.getData().get(j)).getYValue()) {
                ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) series.getData().get(i)).getYValue());
                i++;
                k++;
            } else {
                ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) series.getData().get(j)).getYValue());
                j++;
                k++;
            }
            changeStyleEffect(i, MAIN_THEME, j, MAIN_THEME);
        }
        for (; i <= mid; i++) {
            ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) series.getData().get(i)).getYValue());
            k++;
        }
        for (; j <= high; j++) {
            ((XYChart.Data) copySeries.getData().get(k)).setYValue(((XYChart.Data) series.getData().get(j)).getYValue());
            k++;
        }

        for (int x = low; x <= high; x++) {
            int finalX = x;
            changeStyleEffect(finalX,MAIN_THEME);
            ((XYChart.Data) series.getData().get(x)).setYValue(((XYChart.Data) copySeries.getData().get(x)).getYValue());
            delay();
        }
    }


    // for swap Animation effect
    private ParallelTransition swapAnimation(int d1, int d2) {
        // get the precise location of the node in X axis
        double a1 = ((XYChart.Data) series.getData().get(d1)).getNode().getParent().localToParent(((XYChart.Data) series.getData().get(d1)).getNode().getBoundsInParent()).getMinX();
        double a2 = ((XYChart.Data) series.getData().get(d1)).getNode().getParent().localToParent(((XYChart.Data) series.getData().get(d2)).getNode().getBoundsInParent()).getMinX();

        // if any swap occur then we get the location of our node where it is swapped
        double translated1 = ((XYChart.Data) series.getData().get(d1)).getNode().getTranslateX();
        double translated2 = ((XYChart.Data) series.getData().get(d2)).getNode().getTranslateX();

        TranslateTransition t1 = new TranslateTransition(Duration.millis(DELAY_TIME), ((XYChart.Data) series.getData().get(d1)).getNode());
        t1.setByX(a2 - a1);
        TranslateTransition t2 = new TranslateTransition(Duration.millis(DELAY_TIME), ((XYChart.Data) series.getData().get(d2)).getNode());
        t2.setByX(a1 - a2);
        ParallelTransition pt = new ParallelTransition(t1, t2);
        // ParallelTransition will run t1 and t2 in parallel
        pt.statusProperty().addListener((e, old, curr) -> {
            if (old == Animation.Status.RUNNING) {
                ((XYChart.Data) series.getData().get(d2)).getNode().setTranslateX(translated1);
                ((XYChart.Data) series.getData().get(d1)).getNode().setTranslateX(translated2);

                int temp = (int) ((XYChart.Data) series.getData().get(d2)).getYValue();
                ((XYChart.Data) series.getData().get(d2)).setYValue(((XYChart.Data) series.getData().get(d1)).getYValue());
                ((XYChart.Data) series.getData().get(d1)).setYValue(temp);
            }
        });
        return pt;
    }
}
