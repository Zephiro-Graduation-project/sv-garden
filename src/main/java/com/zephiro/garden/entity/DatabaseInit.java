package com.zephiro.garden.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.zephiro.garden.repository.ShopRepository;

@Controller
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if the database is empty
        if (shopRepository.count() > 0) {
            return;
        }

        // Create all flowers
        List<Flower> flowers = List.of(
            new Flower(1, "Daisy", "Una flor sencilla, símbolo de esperanza y nuevos comienzos.", 30, "Daisy_1", "Daisy_1_Seca"),
            new Flower(2, "Daisy", "Una flor sencilla, símbolo de esperanza y nuevos comienzos.", 30, "Daisy_2", "Daisy_2_Seca"),
            new Flower(3, "Daisy", "Una flor sencilla, símbolo de esperanza y nuevos comienzos.", 30, "Daisy_3", "Daisy_3_Seca"),
        
            new Flower(4, "Estrella", "Brilla por su forma única, reflejando equilibrio y conexión interior.", 60, "Estrella_1", "Estrella_1_Seca"),
            new Flower(5, "Estrella", "Brilla por su forma única, reflejando equilibrio y conexión interior.", 60, "Estrella_2", "Estrella_2_Seca"),
            new Flower(6, "Estrella", "Brilla por su forma única, reflejando equilibrio y conexión interior.", 60, "Estrella_3", "Estrella_3_Seca"),
        
            new Flower(7, "Pansy", "Representa la reflexión, perfecta para momentos de introspección.", 100, "Pansy_1", "Pansy_1_Seca"),
            new Flower(8, "Pansy", "Representa la reflexión, perfecta para momentos de introspección.", 100, "Pansy_2", "Pansy_2_Seca"),
            new Flower(9, "Pansy", "Representa la reflexión, perfecta para momentos de introspección.", 100, "Pansy_3", "Pansy_3_Seca"),
        
            new Flower(10, "Tulipan", "Evoca elegancia y fuerza silenciosa, ideal para quienes perseveran.", 150, "Tulipan_1", "Tulipan_1_Seca"),
            new Flower(11, "Tulipan", "Evoca elegancia y fuerza silenciosa, ideal para quienes perseveran.", 150, "Tulipan_2", "Tulipan_2_Seca"),
            new Flower(12, "Tulipan", "Evoca elegancia y fuerza silenciosa, ideal para quienes perseveran.", 150, "Tulipan_3", "Tulipan_3_Seca"),
        
            new Flower(13, "Loto", "Emblema de sabiduría y pureza, florece incluso en la adversidad.", 200, "Loto_1", "Loto_1_Seca"),
            new Flower(14, "Loto", "Emblema de sabiduría y pureza, florece incluso en la adversidad.", 200, "Loto_2", "Loto_2_Seca"),
            new Flower(15, "Loto", "Emblema de sabiduría y pureza, florece incluso en la adversidad.", 200, "Loto_3", "Loto_3_Seca")
        );
        
        // Create all backgrounds
        List<Background> backgrounds = List.of(
            new Background(1, "Primavera", "Un jardín florecido, lleno de vitalidad y renovación.", 200),
            new Background(2, "Verano", "Un paraíso con sol, playa y arena. Ideal para descansar y divertirse.", 200),
            new Background(3, "Invierno", "Un paisaje nevado, que transmite calma y recogimiento.", 200),
            new Background(4, "Otoño", "Hojas secas, rocas y arbustos teñidos. Perfecto para momentos nostálgicos.", 200),
            new Background(5, "Japonés", "Un entorno sereno con flores de cerezo y un estanque de peces koi. Evoca equilibrio y contemplación.", 300),
            new Background(6, "Mágico", "Un jardín encantado con gnomos y hongos místicos. Ideal para dejar volar la imaginación.", 300)
        );

        // Create all achievements
        List<Achievement> achievements = List.of(
            new Achievement(1, "5 días de racha", "Para conseguir la recompensa se debe realizar el registro diario 5 días consecutivos. ¡Tú puedes!", "one time", 10),
            new Achievement(2, "10 días de racha", "Para conseguir la recompensa se debe realizar el registro diario 10 días consecutivos. ¡Tú puedes!", "one time", 25),
            new Achievement(3, "20 días de racha", "Para conseguir la recompensa se debe realizar el registro diario 20 días consecutivos. ¡Tú puedes!", "one time", 50),
            new Achievement(4, "30 días de racha", "Para conseguir la recompensa se debe realizar el registro diario 30 días consecutivos. ¡Tú puedes!", "one time", 80),
            new Achievement(5, "50 días de racha", "Para conseguir la recompensa se debe realizar el registro diario 50 días consecutivos. ¡Tú puedes!", "one time", 150),
            new Achievement(6, "Registra un contacto de emergencia", "Es importante que registres tu primer contacto de emergencia, para que de ser necesario poder contactarlo.", "one time", 50),
            new Achievement(7, "Consumo de material psicoeducativo", "Gana monedas al consumir alguno de los materiales disponibles en el módulo de contenido.", "daily", 5),
            new Achievement(8, "Registro diario", "Continúa haciendo este registro diariamente. Recuerda que las rachas dan monedas extra.", "daily", 3)
        );

        // Create Zephiro shop
        Shop shop = new Shop("Zephiro", backgrounds, flowers, achievements);
        shopRepository.save(shop);
    }
}
