package com.example.BookingFoodApp.Controllers;

import com.example.BookingFoodApp.Config.AppConstants;
import com.example.BookingFoodApp.Dto.FoodDto;
import com.example.BookingFoodApp.Payloads.ApiResponse;
import com.example.BookingFoodApp.Payloads.FoodResponse;
import com.example.BookingFoodApp.Services.FileService;
import com.example.BookingFoodApp.Services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private FileService fileService;

    ////Đường dẫn image cấu hình trong application.properties
    @Value("${project.image}")
    private String path;

    // Tạo food
    @PostMapping("/")
    public ResponseEntity<FoodDto> createFood(@RequestBody FoodDto foodDto) {
        FoodDto createFoodDto = null;

//        org.apache.tomcat.util.codec.binary.Base64 base64 = new org.apache.tomcat.util.codec.binary.Base64();
//        String decodeText = new String(base64.decode(foodDto.getImage().getBytes()));
//        foodDto.setImage(decodeText);
        createFoodDto = this.foodService.createFood(foodDto);

        return new ResponseEntity<>(createFoodDto, HttpStatus.CREATED);
    }

    // Chỉnh sửa thông tin food
    @PutMapping("/{foodId}")
    public ResponseEntity<FoodDto> updateFood(@Valid @RequestBody FoodDto foodDto, @PathVariable("foodId") Integer fid){
        FoodDto updateFoodDto = this.foodService.updateFood(foodDto, fid);
        return ResponseEntity.ok(updateFoodDto);
    }

    // Xóa food theo id
    @DeleteMapping("/{foodId}")
    public ResponseEntity<ApiResponse> deleteFood(@PathVariable("foodId") Integer fid){
        this.foodService.deleteFood(fid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Food deleted successfully!!",true), HttpStatus.OK);
    }

    // Lấy danh sách food và phân trang
    @GetMapping("/")
    public ResponseEntity<FoodResponse> getAllFoods(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir

    ){
        FoodResponse foodResponse = this.foodService.getAllFood(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<FoodResponse>(foodResponse,HttpStatus.OK);
    }

    // Tìm kiếm food theo id
    @GetMapping("/{foodId}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable("foodId") Integer fid){
        return ResponseEntity.ok(this.foodService.getFoodById(fid));
    }

    ////Lấy danh sách food theo danh mục
    @GetMapping("/category/{cateId}")
    public ResponseEntity<List<FoodDto>> getFoodsByCategory(@PathVariable Integer cateId){
        List<FoodDto> result = this.foodService.getFoodsByCategory(cateId);
        return new ResponseEntity<List<FoodDto>>(result, HttpStatus.OK);
    }

    ////Tìm kiếm food theo từ khóa
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<FoodDto>> searchFoods(@PathVariable String keyword){
        List<FoodDto> result = this.foodService.searchFoods(keyword);
        return new ResponseEntity<List<FoodDto>>(result, HttpStatus.OK);
    }

    // Upload ảnh food
    @PostMapping("/image/upload/{foodId}")
    public ResponseEntity<FoodDto> uploadFoodImage(@RequestParam("image")MultipartFile image,
            @PathVariable Integer foodId) throws IOException {

        FoodDto foodDto = this.foodService.getFoodById(foodId);
        String fileName= this.fileService.uploadImage(path, image);

        foodDto.setImage(fileName);
        FoodDto updateFood = this.foodService.updateFood(foodDto, foodId);
        return new ResponseEntity<FoodDto>(updateFood, HttpStatus.OK);
    }
}
