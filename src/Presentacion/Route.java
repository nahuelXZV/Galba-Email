package Presentacion;

public class Route {

    public void routes(EmailHandler emailHandler) {
        String comando = emailHandler.getComando();
        String parametros = emailHandler.getParametros();
        switch (comando) {
            case "alta":
                System.out.println("Alta");
                break;
            case "baja":
                System.out.println("Baja");
                break;
            case "modificacion":
                System.out.println("Modificacion");
                break;
            case "consulta":
                System.out.println("Consulta");
                break;
            default:
                System.out.println("Subject no valido");
                break;
        }
    }

}
