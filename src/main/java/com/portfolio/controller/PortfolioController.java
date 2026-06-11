package com.portfolio.controller;

import com.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortfolioController {

    private final ProjectRepository projectRepository;
    private final JavaMailSender mailSender;
    private final String recipientEmail;

    public PortfolioController(ProjectRepository projectRepository,
                               JavaMailSender mailSender,
                               @Value("${app.mail.recipient}") String recipientEmail) {
        this.projectRepository = projectRepository;
        this.mailSender = mailSender;
        this.recipientEmail = recipientEmail;
    }

    /**
     * Serves the main portfolio page.
     * Fetches all projects from the H2 database and injects them into the model.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", projectRepository.findAllByOrderByIdAsc());
        return "index";
    }

    /**
     * Health-check / redirect — useful if someone navigates to /index
     */
    @GetMapping("/index")
    public String indexRedirect() {
        return "redirect:/";
    }

    @GetMapping("/photo-workflow")
    public String photoWorkflow() {
        return "photo-workflow";
    }

    @GetMapping("/church-appointment")
    public String churchAppointment() {
        return "church-appointment";
    }

    @GetMapping("/trendvelle")
    public String trendvelle() {
        return "trendvelle";
    }

    @PostMapping({"/", "/index"})
    public String handleContactForm(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "message", required = false) String message,
            Model model
    ) {
        model.addAttribute("projects", projectRepository.findAllByOrderByIdAsc());
        model.addAttribute("formSubmitted", true);
        model.addAttribute("firstName", firstName);
        model.addAttribute("subject", subject);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(recipientEmail);
            if (email != null && !email.isBlank()) {
                mailMessage.setReplyTo(email);
            }
            mailMessage.setSubject("Portfolio Contact Form: " + (subject == null ? "New message" : subject));
            mailMessage.setText("From: " + firstName + " " + lastName + " <" + email + ">\n\n"
                    + "Message:\n" + message + "\n\n"
                    + "---\nThis email was submitted from the portfolio contact form.");
            mailSender.send(mailMessage);
            model.addAttribute("emailStatus", "Message sent successfully.");
        } catch (Exception ex) {
            model.addAttribute("emailStatus", "Unable to send message. Please try again later.");
        }

        return "index";
    }
}
