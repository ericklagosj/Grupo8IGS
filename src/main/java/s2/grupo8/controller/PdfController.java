package s2.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import s2.grupo8.service.PdfGenerationService;

@Controller
@RequestMapping("/pdf")
public class PdfController {

        @Autowired
    private PdfGenerationService pdfGenerationService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestParam Long promedioId) {
        byte[] pdfBytes = pdfGenerationService.generatePdf(promedioId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "Informe.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
