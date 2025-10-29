import java.awt.*;
import java.awt.print.*;
import javax.swing.*;

public class PDFExporter {
    public static void exportResumeToPDF(Resume resume, String filePath) throws Exception {
        // Create a printable component for the resume
        PrintableResume printableResume = new PrintableResume(resume);
        
        // Get a PrinterJob
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(printableResume);
        
        // Show print dialog
        if (job.printDialog()) {
            // Set the file output format to PDF
            job.print();
            
            // For actual PDF file creation, we'll use pageable and create a PDF file
            // Note: This creates a print-ready document that can be saved as PDF
            // when user selects "Microsoft Print to PDF" or similar PDF printer
            JOptionPane.showMessageDialog(null, 
                "Please select 'Microsoft Print to PDF' or your PDF printer from the printer list.\n" +
                "Then save the file to: " + filePath,
                "Print to PDF", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

class PrintableResume implements Printable {
    private Resume resume;
    
    public PrintableResume(Resume resume) {
        this.resume = resume;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        // Set font and color
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.BLACK);
        
        int y = 50;
        int margin = 50;
        
        // Title
        g2d.drawString("PROFESSIONAL RESUME", margin, y);
        y += 30;
        
        // Personal Information
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("PERSONAL INFORMATION", margin, y);
        y += 20;
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        if (resume.getFullName() != null && !resume.getFullName().isEmpty()) {
            g2d.drawString("Name: " + resume.getFullName(), margin, y);
            y += 15;
        }
        if (resume.getEmail() != null && !resume.getEmail().isEmpty()) {
            g2d.drawString("Email: " + resume.getEmail(), margin, y);
            y += 15;
        }
        if (resume.getPhone() != null && !resume.getPhone().isEmpty()) {
            g2d.drawString("Phone: " + resume.getPhone(), margin, y);
            y += 15;
        }
        if (resume.getAddress() != null && !resume.getAddress().isEmpty()) {
            g2d.drawString("Address: " + resume.getAddress(), margin, y);
            y += 15;
        }
        if (resume.getLinkedIn() != null && !resume.getLinkedIn().isEmpty()) {
            g2d.drawString("LinkedIn: " + resume.getLinkedIn(), margin, y);
            y += 15;
        }
        if (resume.getSummary() != null && !resume.getSummary().isEmpty()) {
            g2d.drawString("Summary: " + resume.getSummary(), margin, y);
            y += 20;
        }
        
        y += 10;
        
        // Education
        if (!resume.getEducation().isEmpty()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("EDUCATION", margin, y);
            y += 20;
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            for (Education edu : resume.getEducation()) {
                g2d.drawString("• " + edu.toString(), margin, y);
                y += 15;
            }
            y += 5;
        }
        
        // Experience
        if (!resume.getExperience().isEmpty()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("EXPERIENCE", margin, y);
            y += 20;
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            for (Experience exp : resume.getExperience()) {
                String[] lines = splitString(exp.toString(), 80);
                for (String line : lines) {
                    g2d.drawString(line, margin, y);
                    y += 15;
                }
                y += 5;
            }
        }
        
        // Skills
        if (!resume.getSkills().isEmpty()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("SKILLS", margin, y);
            y += 20;
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            for (String skill : resume.getSkills()) {
                g2d.drawString("• " + skill, margin, y);
                y += 15;
            }
        }
        
        return PAGE_EXISTS;
    }
    
    private String[] splitString(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return new String[]{text};
        }
        
        java.util.List<String> lines = new java.util.ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + maxLength, text.length());
            if (end < text.length()) {
                // Try to break at a space
                int breakPoint = text.lastIndexOf(' ', end);
                if (breakPoint > start) {
                    end = breakPoint;
                }
            }
            lines.add(text.substring(start, end).trim());
            start = end + 1;
        }
        
        return lines.toArray(new String[0]);
    }
}