package org.example;

import lombok.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Empleado {

    @Getter @Setter
    String nombre;
    @Getter @Setter @NonNull
    LocalDate fechaIngreso;
    @Getter @Setter
    int vacacionesTomadas;


    public String toString(){

        DecimalFormat f = new DecimalFormat("##.00");
        double diasVacaciones = calcularVacacionesProporcionalesNUEVAley();

        return "Nombre: " + nombre +
                "\nFecha de ingreso: " + fechaIngreso.toString() +
                "\nVacaciones: " + f.format(diasVacaciones) +
                "\nVacaciones tomadas: " + vacacionesTomadas +
                "\nVacaciones restantes: " + f.format(diasVacaciones-vacacionesTomadas);
    }



    // Calcula los dias de vacaciones por años completos y el proporcional al ultimo año con la NUEVA ley
    public double calcularVacacionesProporcionalesNUEVAley() {

        double añosTotales =Period.between(fechaIngreso, LocalDate.now()).getYears();
        double añosViejaLey = getAñosHastaAniversario2022();

        double total = calcularVacacionesViejaLey();
        int diasVacas = 12;
        int i;

        // Se recorrerán los años para evaluar los dias de vacaciones correspondientes
        for (i = 1; i <= 5 && i <= añosTotales; i++, diasVacas += 2)
            if(i >= añosViejaLey) // Si los años actuales ya superaron a los de la nueva ley, entonces se agregan los dias de vacaciones nuevos
                total += diasVacas;

        for (; i < añosTotales; i++)
        {
            if(i >= añosViejaLey) // Si los años actuales ya superaron a los de la nueva ley, entonces se agregan los dias de vacaciones nuevos
                total += diasVacas;

            if (i % 5 == 0) // Cada 5 años se incrementan los dias de vacaciones en 2
                diasVacas += 2;
        }

        // Si en el año actual es multiplo de 5 entonces corresponde un incremento de dias de vacaciones
        if (i % 5 == 0)
            diasVacas += 2;

        // Se calculan los dias de vacaciones proporcionales
        double diasProporcionales = ((getAñosDesdeAniversario2022() % 1) * diasVacas);

        // regresa con 2 decimales
        return Math.round((total + diasProporcionales) * 100.0) / 100.0;

    }


    public double calcularVacacionesViejaLey()    {

        double años = getAñosHastaAniversario2022();
        double totalVacaciones = 0;
        int vacacionesPorAño = 6;

        for(int i = 1; i <= 5 && años > 0; i++, años--, vacacionesPorAño += 2)
            totalVacaciones += vacacionesPorAño;

        vacacionesPorAño -= 2;

        for (int i = 1; i <= 4 && años > 0; i++, años--)
            totalVacaciones += vacacionesPorAño;

        vacacionesPorAño += 2;

        for (int i = 1; años > 0; i++, años--)
        {
            totalVacaciones += vacacionesPorAño;

            if (i % 5 == 0)
                vacacionesPorAño += 2;
        }
        return totalVacaciones;

    }

    // Obtiene años trabajados hasta el cambio de ley del 2022
    public double getAñosHastaAniversario2022() {
        LocalDate aniversario = LocalDate.of(
                2022,
                fechaIngreso.getMonth(),
                fechaIngreso.getDayOfMonth());

        return Period.between(fechaIngreso, aniversario).getYears();
    }

    // Obtiene los años trabajados desde el cambio de ley del 2022
    public double getAñosDesdeAniversario2022() {
        LocalDate aniversario = LocalDate.of(
                2022,
                fechaIngreso.getMonth(),
                fechaIngreso.getDayOfMonth());

        Period period = Period.between(aniversario, LocalDate.now());

        // Devuelve con numero decimal
        return Math.round((period.getYears() + ((double)(period.getMonths() * 30 + period.getDays()) / 365)) * 100.0) / 100.0;
    }

}
