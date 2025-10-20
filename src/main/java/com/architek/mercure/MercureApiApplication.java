package com.architek.mercure;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.architek.mercure.entities.Car;
import com.architek.mercure.entities.Image;
import com.architek.mercure.repositories.CarRepository;
import com.architek.mercure.repositories.ImageRepository;

@SpringBootApplication
public class MercureApiApplication implements CommandLineRunner {

	@Autowired
	private CarRepository carRepo;

	@Autowired
	private ImageRepository imageRepo;

	public static void main(String[] args) {
		SpringApplication.run(MercureApiApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        
		//String s3BucketPath = "/Users/morad/architek-consulting/s3-local/mercure-bucket/";
		
		generateCar("AUDI","Q3");
		generateCar("AUDI","Q2");
		generateCar("BMW","X1");
		generateCar("BMW","X3");

    }

    private void generateCar(String brand, String model) {
        
		Car c1 = Car.builder()
                .uuid(UUID.randomUUID())
                .brand(brand)
                .model(model)
                .build();
        c1 = carRepo.save(c1);
        
        Image i1c1 = Image.builder().car(c1).main(true).path("1.jpg").build();
        Image i2c1 = Image.builder().car(c1).main(false).path("2.jpg").build();
        Image i3c1 = Image.builder().car(c1).main(false).path("3.jpg").build();
        
        imageRepo.save(i1c1);
        imageRepo.save(i2c1);
        imageRepo.save(i3c1);
    }

}
