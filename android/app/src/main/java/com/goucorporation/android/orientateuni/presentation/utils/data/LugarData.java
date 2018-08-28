/*
 *
 */

package com.goucorporation.android.orientateuni.presentation.utils.data;

import com.google.android.gms.maps.model.LatLng;
import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.Lugar;

import java.util.ArrayList;
import java.util.List;

public class LugarData {
    public static List<Lugar> lugares = new ArrayList<>();
    static {
        lugares.add(new Lugar("A","Facultad de Ingeniería Mecánica – FIM", R.drawable.imagen_01_edited,new LatLng(-12.024194, -77.047722),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("B","Pabellón Central",R.drawable.imagen_04_edited,new LatLng(-12.023444, -77.048167),Lugar.TIPO_OTRO));
        lugares.add(new Lugar("C","Facultad de Ingeniería Química y Textil – FIQT",R.drawable.imagen_03_edited,new LatLng(-12.022944, -77.047611),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("D","Facultad de Ingeniería Ambiental – FIA",R.drawable.imagen_06_edited,new LatLng(-12.022806, -77.048861),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("E","Gran Teatro UNI",R.drawable.imagen_05_edited,new LatLng(-12.023472, -77.049056),Lugar.TIPO_OTRO));
        lugares.add(new Lugar("F","Gimnasio UNI",R.drawable.imagen_07_edited,new LatLng(-12.022500, -77.047806),Lugar.TIPO_OTRO));
        lugares.add(new Lugar("F","Coliseo de la UNI",R.drawable.imagen_09_edited,new LatLng(-12.021056, -77.048306),Lugar.TIPO_OTRO));
        lugares.add(new Lugar("G","Facultad de Ingeniería Civil – FIC",R.drawable.imagen_10_edited,new LatLng(-12.022472, -77.049250),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("H","Facultad de Arquitectura, Urbanismo y Artes – FAUA",R.drawable.imagen_11_edited,new LatLng(-12.021472, -77.049667),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("I","Facultad de Ingeniería Geológica, Minera y Metalúrgica – FIGMM",R.drawable.imagen_12_edited,new LatLng(-12.019917, -77.048333),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("J","Pabellón J",R.drawable.imagen_14_edited,new LatLng(-12.020000, -77.049389),Lugar.TIPO_OTRO));
        lugares.add(new Lugar("M","Facultad de Ingeniería Económica, Estadística y Ciencias Sociales – FIEECS",R.drawable.imagen_15_edited,new LatLng(-12.019694, -77.049194),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("N","Puerta de ingreso ADMISIÓN UNI",R.drawable.imagen_13_edited,new LatLng(-12.019917, -77.050167),Lugar.TIPO_OTRO));
        lugares.add(new Lugar("Q","Facultad de Ingeniería Eléctrica, Electrónica – FIEE",R.drawable.imagen_17_edited,new LatLng(-12.017444, -77.049556),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("R","Facultad de Ciencias FC",R.drawable.imagen_18_edited,new LatLng(-12.017611, -77.050639),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("R","Facultad de Ingeniería de Petróleo, Gas Natural y Petroquímica – FIP",R.drawable.imagen_20_edited,new LatLng(-12.016361, -77.051028),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("S","Facultad de Ingeniería Industrial y de Sistemas FIIS",R.drawable.imagen_19_edited,new LatLng(-12.015861, -77.050333),Lugar.TIPO_FACULTAD));
        lugares.add(new Lugar("Pt3","Puerta N° 03",R.drawable.imagen_02_edited,new LatLng(-12.024639, -77.048167),Lugar.TIPO_PUERTA));
        lugares.add(new Lugar("Pt4","Puerta N° 04",R.drawable.imagen_08_edited,new LatLng(-12.023194, -77.049917),Lugar.TIPO_PUERTA));
        lugares.add(new Lugar("Pt5","Puerta N° 05",R.drawable.imagen_16_edited,new LatLng(-12.017944, -77.050806),Lugar.TIPO_PUERTA));
        lugares.add(new Lugar("Pt6","Puerta N° 06",R.drawable.imagen_21_edited,new LatLng(-12.015167, -77.051833),Lugar.TIPO_PUERTA));
    }
}
