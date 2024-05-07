package src.main.java.com.byteWise.ui;

import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Adjustments {

     static public double calculateFontSize(Text text, int textLength, int maxWidth, int maxHeight) {
        // Ensure positive bounds
        if (maxWidth <= 0 || maxHeight <= 0) {
            throw new IllegalArgumentException("Invalid bounds: width and height must be positive.");
        }

        // Initial font size estimate (adjust as needed)
        double estimatedFontSize = Math.min(maxWidth, maxHeight); // Start with smaller of width or height

        // Binary search for optimal font size
        double low = 0;
        double high = estimatedFontSize;
        while (low <= high) {
            double midFontSize = (low + high) / 2;
            text.setFont(Font.font(text.getFont().getName(), midFontSize));

            // Measure text dimensions using getLayoutBounds()
            Bounds textBounds = text.getLayoutBounds();
            double textWidth = textBounds.getWidth();
            double textHeight = textBounds.getHeight();

            if (textWidth <= maxWidth && textHeight <= maxHeight) {
                low = midFontSize + 0.1; // Adjust search step size as needed
            } else {
                high = midFontSize - 0.1; // Adjust search step size as needed
            }
        }

        // Return the final (slightly reduced) font size as the optimal value
        return Math.min(38, high - 0.1); 
    }
    
    static public void adjustUserFontSize(Text userName){
        userName.textProperty().addListener((observable, oldValue, newValue) -> {
        double newFontSize = Adjustments.calculateFontSize(userName,newValue.length(),254,89);
        userName.setFont(Font.font(userName.getFont().getName(), newFontSize));
        });
    }
}
