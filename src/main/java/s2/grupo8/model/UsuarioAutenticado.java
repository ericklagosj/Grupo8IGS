package s2.grupo8.model;

public class UsuarioAutenticado {

    private static Usuario usuarioAutenticado;

    public static Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public static void setUsuarioAutenticado(Usuario usuario) {
        usuarioAutenticado = usuario;
    }
}
