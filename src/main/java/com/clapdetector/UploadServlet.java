package com.clapdetector;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String uploadDirPath = "/home/ayyappankalai/ClapDetector/uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        try {
            Part filePart = request.getPart("audioFile");
            String fileName = new File(filePart.getSubmittedFileName()).getName();
            String uploadedFilePath = uploadDirPath + File.separator + fileName;
            filePart.write(uploadedFilePath);

            // Run the backend detector on uploaded file
            ProcessBuilder pb = new ProcessBuilder("java", "-cp",
                "/home/ayyappankalai/ClapDetector/target/classes:/home/ayyappankalai/ClapDetector/TarsosDSP-2.4.jar",
                "com.clapdetector.SoundApp", uploadedFilePath
            );

            pb.directory(new File("/home/ayyappankalai/ClapDetector"));
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            out.println("<h3>🔍 Detection Result:</h3><pre>");
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
            out.println("</pre>");
            process.waitFor();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}

