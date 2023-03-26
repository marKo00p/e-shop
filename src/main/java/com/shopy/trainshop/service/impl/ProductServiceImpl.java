package com.shopy.trainshop.service.impl;

import com.shopy.trainshop.dao.CategoryRepository;
import com.shopy.trainshop.dao.ProductRepository;
import com.shopy.trainshop.domain.Category;
import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.ProductService;
import com.shopy.trainshop.service.UserService;
import com.shopy.trainshop.utils.ImageUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ImageUpload imageUpload;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, ImageUpload imageUpload, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.imageUpload = imageUpload;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDTO> getAll() {

        return productRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

 
    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public List<Product> filterHighPrice() {
        return productRepository.filterHighPrice();
    }

    @Override
    public List<Product> filterLowPrice() {
        return productRepository.filterLowPrice();
    }
    @Override
    public List<Product> findByKeyword(String keyword){
        return productRepository.findByKeyword(keyword);
    }

    @Override
    public boolean saveProduct(MultipartFile file, String title, BigDecimal price, String description, Integer quantity) {
        if(file == null && title==null && price==null && description==null && quantity==null){
            throw new IllegalArgumentException("It's impossible to save the product");
        }
    Product product = new Product();
        try{
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if(fileName.contains(".."))
            {
                System.out.println("not a valid file");
            }
//                product.setImage(resizeImageForUse(Base64.getEncoder().encodeToString(file.getBytes()), 400,400));
            product.setImage((Base64.getEncoder().encodeToString(file.getBytes())));
            product.setTitle(title);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            productRepository.save(product);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
}

    @Override
    public void deleteById(Long productId) {
         productRepository
                .deleteById(productId);
    }
    @Override
    public boolean updateProduct(MultipartFile file, Long id, String title, BigDecimal price, String description, Integer quantity) {
        if (file == null && title == null && price == null && description == null && quantity == null) {
            throw new IllegalArgumentException("It's impossible to save the product");
        }
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                System.out.println("not a valid file");
            }
            Product updatedProductDTO = productRepository.findProductById(id);
            updatedProductDTO.setTitle(title);
            updatedProductDTO.setDescription(description);
            updatedProductDTO.setPrice(price);
            updatedProductDTO.setQuantity(quantity);
            updatedProductDTO.setImage((Base64.getEncoder().encodeToString(file.getBytes())));
            productRepository.save(updatedProductDTO);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public boolean updateProduct(ProductDTO productDTO) {
//        if(productDTO == null){
//            throw new RuntimeException("It's impossible to save the product");
//        }
//        Product updatedProductDTO = productRepository.findProductById(productDTO.getId());
//        updatedProductDTO.setTitle(productDTO.getTitle());
//        updatedProductDTO.setDescription(productDTO.getDescription());
//        updatedProductDTO.setPrice(productDTO.getPrice());
//        updatedProductDTO.setQuantity(productDTO.getQuantity());
//        updatedProductDTO.setImage(productDTO.getImage());
//        productRepository.save(updatedProductDTO);
//        return true;
//    }



    private ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .image(product.getImage())
                .build();
    }

//    // No API
//    public String resizeImageForUse(String src,int width, int height) {
//        BufferedImage image = base64ToBufferedImage(src);
//        try {
//            image = resizeImage(image, width,height);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            return bufferedImageTobase64(image);
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }
//    private  BufferedImage resizeImage(BufferedImage image , int width , int height) throws IOException {
//        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
//        try {
//            Thumbnails.of(image).size(width, height).outputFormat("JPEG").outputQuality(1).toOutputStream(outputstream);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        byte[] data = outputstream.toByteArray();
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
//        return ImageIO.read(inputStream);
//    }
//    private BufferedImage base64ToBufferedImage(String base64Img) {
//        BufferedImage image = null;
//        byte[] decodedBytes = Base64.getDecoder().decode(base64Img);
//
//        try {
//            image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return image;
//    }
//
//    private String bufferedImageTobase64(BufferedImage image ) throws UnsupportedEncodingException {
//        final ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(image, "JPEG", Base64.getEncoder().wrap(out));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return out.toString(StandardCharsets.ISO_8859_1.name());  }


}
