import org.example.Empleado;
import org.example.Sistema;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;


public class SistemaTest {

    Empleado empleado;
    ByteArrayOutputStream out;

    @Before
    public void before(){
        out = new ByteArrayOutputStream();
    }


    @Test
    public void añosViejaLey(){
        empleado = new Empleado("Luis", LocalDate.of(1992,8,13), 0);
        Assert.assertEquals(30, empleado.getAñosHastaAniversario2022(),0);
    }

    @Test
    public void añosNuevaLey(){
        empleado = new Empleado("Luis", LocalDate.of(1992,8,13), 0);
        Assert.assertEquals(0.55, empleado.getAñosDesdeAniversario2022(),0);
    }

    @Test
    public void totalDeDias_Luis(){
        empleado = new Empleado("Luis", LocalDate.of(1992,8,13), 0);
        Assert.assertEquals(527.6 , empleado.calcularVacacionesProporcionalesNUEVAley(), 0);
    }

    @Test
    public void totalDeDias_Araceli(){
        empleado = new Empleado("Arroyo Aldana Araceli", LocalDate.of(2010,7,16), 141);
        Assert.assertEquals(168.88 , empleado.calcularVacacionesProporcionalesNUEVAley(), 0);
    }

    @Test
    public void totalDeDias_ElPepe(){
        empleado = new Empleado("El pepe", LocalDate.of(2022,3,5), 141);
        Assert.assertEquals(12 , empleado.calcularVacacionesProporcionalesNUEVAley(), 0);
    }





}
