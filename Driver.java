public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = {6, 0, 0, 5};
        Polynomial p1 = new Polynomial(c1);
        double[] c2 = {0, -2, 0, 0, -9};
        Polynomial p2 = new Polynomial(c2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        double[] c3 = {1, -2, 5};
        int[] e3 = {0, 1, 2};
        Polynomial p3 = new Polynomial(c3, e3);
        double[] c4 = {2, 3};
        int[] e4 = {0, 1};
        Polynomial p4 = new Polynomial(c4, e4);
        Polynomial product = p3.multiply(p4);
        System.out.println("Product(0) = " + product.evaluate(0));
        System.out.println("Product(1) = " + product.evaluate(1));

        String tempPoly = "5-3x2+7x8";
        try (java.io.PrintWriter writer = new java.io.PrintWriter("temp.txt")) {
            writer.println(tempPoly);
            writer.close();
            Polynomial p5 = new Polynomial(new java.io.File("temp.txt"));
            System.out.println("p5(0) = " + p5.evaluate(0));
            p5.saveToFile("tempOutput.txt");
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("tempOutput.txt"));
            System.out.println(reader.readLine());
            reader.close();
            new java.io.File("temp.txt").delete();
            new java.io.File("tempOutput.txt").delete();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}