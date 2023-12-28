package s2.grupo8.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import s2.grupo8.model.RolUsuario;
import s2.grupo8.model.Usuario;
import s2.grupo8.repository.UsuarioRepository;
import s2.grupo8.exception.CredencialesInvalidasException;
import s2.grupo8.model.UsuarioAutenticado;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> obtenerEvaluadores() {
        return usuarioRepository.findByRol(RolUsuario.EVALUADOR);
    }


    public Usuario findById(long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void deleteById(long id) {
        usuarioRepository.deleteById(id);
    }

    public void guardarContrasena(Long usuarioId, String nuevaContrasena) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            
            usuario.setPassword(nuevaContrasena);
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("No se encontró el usuario con ID " + usuarioId);            
        }
    }

    public void enviarCredenciales(Usuario usuario) {
        String asunto = "Credenciales de acceso";
        String cuerpo = "Bienvenido al sistema de Grupo 8 S2, tus credenciales son:\n"
                + "Usuario: " + usuario.getId() + "\n"
                + "Contraseña: " + usuario.getPassword();

        enviarCorreoElectronico(usuario.getEmail(), asunto, cuerpo);
    }

    private void enviarCorreoElectronico(String destinatario, String asunto, String cuerpo) {
        String correoRemitente = "ingsoftwareg8@gmail.com";
        String contraseñaRemitente = "aogu msma kome lttg";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoRemitente, contraseñaRemitente);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);

            System.out.println("Correo enviado a: " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validarCredenciales(String id, String password) {
        Usuario usuario = usuarioRepository.findById(Long.parseLong(id));

        return usuario != null && usuario.validarCredenciales(id, password);
    }


    public List<Usuario> obtenerUsuariosPorRol(RolUsuario evaluador) {
        return null;
    }

    @Transactional
    public void autenticarUsuario(Long id, String password) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();

        if (!usuario.validarCredenciales(id.toString(), password)) {
            throw new CredencialesInvalidasException("Credenciales inválidas");
        }
        UsuarioAutenticado.setUsuarioAutenticado(usuario);
    }
}
