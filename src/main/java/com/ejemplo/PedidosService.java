public class PedidosService {

    private DescuentoRepository repository;

    public PedidoService(DescuentoRepository repository) {
        this.repository = repository;
    }

    public double calcularTotal(double subtotal, String codigoDescuento, boolean envioExpress) {
        double descuento = repository.obtenerPorcentaje(codigoDescuento);
        double envio = envioExpress ? 20.0 : 10.0;
        return (subtotal * (1 - descuento)) + envio;
    }
}