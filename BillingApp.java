public class BillingApp {

    public record ServiceOrder(String clientName, int hours, double hourRate) {}

    @FunctionalInterface
    public interface PriceStrategy {
        double calculate(ServiceOrder order);
    }

    public static class PriceCalculator {
        public double calculate(ServiceOrder order, PriceStrategy strategy) {
            return strategy.calculate(order);
        }
    }

    public static void main(String[] args) {
        ServiceOrder order = new ServiceOrder("Alpha Company", 10, 120.0);
        PriceCalculator calculator = new PriceCalculator();

        PriceStrategy standard = o -> o.hours() * o.hourRate();
        PriceStrategy discount = o -> o.hours() * o.hourRate() * 0.90;
        PriceStrategy weekend = o -> o.hours() * o.hourRate() * 1.25;

        System.out.println("Standard: " + calculator.calculate(order, standard));
        System.out.println("Discount: " + calculator.calculate(order, discount));
        System.out.println("Weekend:  " + calculator.calculate(order, weekend));
    }
}