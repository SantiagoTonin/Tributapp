/*los siguientes son arreglos y constantes con los datos de impuestos a locación
y prestación de servicios, venta de cosas muebles y aportes jubilatorios,
ordenados de menor a mayor categoría, para ser llamados dependiendo en cual
estamos.
*/

const CONSTANTS = {
    S_2021: {
        ARRAY_IMP_SERVICIOS: [228.63, 440.49, 753.19, 1237.37, 2353.69, 3238.03, 4118.99, 9414.8],
        ARRAY_IMP_VENTA: [
            228.63, 440.49, 696.01, 1143.23, 1825.79, 2383.95, 2972.36, 7296.5, 11768.52, 13829.7,
            15887.51,
        ],
        ARRAY_APORTES_JUBILATORIS: [
            1008.72, 1109.59, 1220.56, 1342.61, 1476.88, 1624.56, 1787.01, 1965.71, 2162.29, 2378.53,
            2616.36,
        ],
        ARRAY_MAX_CAT: [
            370000.0, 550000.0, 770000.0, 1060000.0, 1400000.0, 1750000.0, 2100000.0, 2600000.0,
            2910000.0, 3335000.0, 3700000.0,
        ],
        IMPUESTO_OBRA_SOCIAL: 1408.87,
    },
    P_2022: {
        ARRAY_IMP_SERVICIOS: [288.07, 555.02, 949.02, 1559.09, 2965.66, 4079.93, 5189.94, 11862.69],
        ARRAY_IMP_VENTA: [
            288.07, 555.02, 876.97, 1440.47, 2300.5, 3003.79, 3745.19, 9193.62, 14828.38, 17425.48,
            20018.33,
        ],
        ARRAY_APORTES_JUBILATORIS: [
            1270.99, 1389.09, 1537.91, 1691.69, 1860.87, 2046.95, 2251.64, 2476.8, 2724.49, 2996.96,
            3296.62,
        ],
        ARRAY_MAX_CAT: [
            466201.59, 693002.36, 970203.3, 1335604.55, 1764006.01, 2205007.51, 2646009.01, 3276011.15,
            3666612.48, 4202114.31, 4662015.87,
        ],
        IMPUESTO_OBRA_SOCIAL: 1775.18,
    },
    S_2022: {
        ARRAY_IMP_SERVICIOS: [288.07, 555.02, 949.02, 1559.09, 2965.66, 4079.93, 5189.94, 11862.69],
        ARRAY_IMP_VENTA: [
            288.07, 555.02, 876.97, 1440.47, 2300.5, 3003.79, 3745.19, 9193.62, 14828.38, 17425.48,
            20018.33,
        ],
        ARRAY_APORTES_JUBILATORIS: [
            1270.99, 1389.09, 1537.91, 1691.69, 1860.87, 2046.95, 2251.64, 2476.8, 2724.49, 2996.96,
            3296.62,
        ],
        ARRAY_MAX_CAT: [
            748382.07, 1112459.83, 1557443.75, 1934273.04, 2277684.56, 2847105.70, 3416526.83, 4229985.60,
            4734330.3, 5425770, 6019594.89,
        ],
        IMPUESTO_OBRA_SOCIAL: 1775.18,
    },
};



const MAX_LOCACION = 7;
const MAX_MUEBLES = 10;
const A_ASCII = 65;

$(document).ready(function() {
    // month parsing
    semester_idx = "S_2022";
    $("#select_month").on("change", function() {
        semester_idx = document.getElementById("select_month").value;
    });
    $("#actividad").on("change", function() {
        var actividad = document.getElementById("actividad").value;
    });
    $("#situacion_laboral").on("change", function() {
        var situacion_laboral = document.getElementById("situacion_laboral").value;
    });
    $("#button_calculate").on("click", function() {
        var n = $("#facturacion").val();

        var error = 0;
        var jubilacion = 0;
        var obra_social = 0;
        var imp = 0;
        var total = 0;

        //cat es la categoría de monotributo, empezando desde la A = 0
        var cat = "A";

        //abajo estan los maximos de cada categoría
        if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[0]) {
            cat = "A";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[1]) {
            cat = "B";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[2]) {
            cat = "C";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[3]) {
            cat = "D";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[4]) {
            cat = "E";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[5]) {
            cat = "F";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[6]) {
            cat = "G";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[7]) {
            cat = "H";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[8]) {
            cat = "I";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[9]) {
            cat = "J";
        } else if (n <= CONSTANTS[semester_idx].ARRAY_MAX_CAT[10]) {
            cat = "K";
        } else if (n >= CONSTANTS[semester_idx].ARRAY_MAX_CAT[10]) {
            cat = "L";
        }

        if (n <= 1000) {
            error = 3;
        }

        var i = cat.charCodeAt(0) - A_ASCII;
        actividad = $("#actividad").val();
        situacion_laboral = $("#situacion_laboral").val();

        if (actividad == 1) {
            //locación o prestación de servicios
            if (i > MAX_LOCACION) {
                //categoría mayor a H
                error = 1;
            } else {
                imp = CONSTANTS[semester_idx].ARRAY_IMP_SERVICIOS[i];
            }
        } else {
            //venta de cosas muebles
            if (i > MAX_MUEBLES) {
                //categoría mayor a K
                error = 2;
            } else {
                imp = CONSTANTS[semester_idx].ARRAY_IMP_VENTA[i];
            }
        }

        if (situacion_laboral == 1) {
            //si es actividad independiente,debe pagar aportes
            jubilacion = CONSTANTS[semester_idx].ARRAY_APORTES_JUBILATORIS[i];
            obra_social = CONSTANTS[semester_idx].IMPUESTO_OBRA_SOCIAL;
        }
        if (situacion_laboral == 2 || actividad == 2) {
            error = 4;
        }
        //resultados
        var tcat = document.getElementById("text_cat");
        var tjub = document.getElementById("text_jubilacion");
        var timp = document.getElementById("text_impuestos");
        var tobra = document.getElementById("text_obra_social");
        var ttotal = document.getElementById("text_total");

        if (!error) {
            total = imp + jubilacion + obra_social;
            total = Number(total.toFixed(2));
            tcat.textContent = "Categoría ".concat(cat);
            ttotal.textContent = "Cuota total: $ ".concat(total);
            timp.textContent = "- Impuesto : $ ".concat(imp);
            tobra.textContent = "- Aportes obra social: $ ".concat(obra_social);
            tjub.textContent = "- Aportes jubilación: $ ".concat(jubilacion);
        } else if (error) {
            tcat.textContent = "No corresponde Monotributo";
            ttotal.textContent = "";
            timp.textContent = "";
            tobra.textContent = "";
            if (error == 1) {
                tjub.textContent =
                    "La facturación máxima para locación/prestación de servicios es $ ".concat(
                        CONSTANTS[semester_idx].ARRAY_MAX_CAT[7]
                    );
            } else if (error == 2) {
                tjub.textContent = "La facturación máxima para venta de cosas muebles es $ ".concat(
                    CONSTANTS[semester_idx].ARRAY_MAX_CAT[10]
                );
            } else if (error == 3) {
                tcat.textContent = "Hubo un error";
                tjub.textContent =
                    'El importe es muy pequeño o negativo, evite usar "-" y use puntos solo para separar los decimales';
            } else if (error == 4) {
                tcat.textContent = "Hubo un error";
                tjub.textContent = "No seleccionó todas las opciones";
            }
        }
    });
});