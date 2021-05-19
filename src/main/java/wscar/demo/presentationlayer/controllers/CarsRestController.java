package wscar.demo.presentationlayer.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import wscar.demo.businesslayer.services.CarsService;
import wscar.demo.datalayer.dto.Carsdto;
import wscar.demo.datalayer.entities.Cars;

@RestController
@RequestMapping("api/cars")
public class CarsRestController {

    private CarsService carsService;
    private ModelMapper modelMapper;

    public CarsRestController(CarsService carsService, ModelMapper modelMapper){
        this.carsService = carsService;
        this.modelMapper = modelMapper;}

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllCars(
            @RequestParam(name="_page", defaultValue = "1") Integer pageNum,
            @RequestParam(name="_limit", defaultValue = "10") Integer pageSize
    ){
        try{
            Page<Cars> page = carsService.getAllCars(pageNum,pageSize);

            List<Cars> carsList = page.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("cars", carsList.stream().map(x -> modelMapper.map(x, Carsdto.class)));
            response.put("currentPage", page.getNumber() + 1);
            response.put("totalItems", page.getTotalElements());
            response.put("totalPages", page.getTotalPages());

            return ResponseEntity.ok(response);

        }catch (Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCarsById(@PathVariable Integer id){
        try{
            Cars c = carsService.getCarById(id);
            return ResponseEntity.ok(modelMapper.map(c,Carsdto.class));
        }
        catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<?> addNewCar(@RequestBody Cars cars){

        try{
            Cars c = carsService.addNewCar(cars);
            return ResponseEntity.ok(modelMapper.map(c,Carsdto.class));

        } catch (Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carsdto> updateCars(@PathVariable Integer id, @RequestBody Cars cars){

        try {
            cars.setId(id);
            Cars c = carsService.updateCar(cars);
            return ResponseEntity.ok(modelMapper.map(c,Carsdto.class));
        }
        catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Carsdto> deleteCars(@PathVariable Integer id){
        try{
            Cars c = carsService.getCarById(id);
            carsService.deleteCar(c);
            return ResponseEntity.ok(modelMapper.map(c,Carsdto.class));
        }
        catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }
}
