package com.shopy.trainshop.service.impl;

import com.shopy.trainshop.dao.BucketItemRepository;
import com.shopy.trainshop.dao.BucketRepository;
import com.shopy.trainshop.dao.ProductRepository;
import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.BucketItem;
import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.dto.UserDTO;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.ProductService;
import com.shopy.trainshop.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final ProductService productService;
    private final BucketItemRepository bucketItemRepository;

    public BucketServiceImpl(BucketRepository bucketRepository, ProductService productService, BucketItemRepository bucketItemRepository) {
        this.bucketRepository = bucketRepository;
        this.productService = productService;
        this.bucketItemRepository = bucketItemRepository;
    }
    @Override
    public Bucket addItemToCart(Long id, String sessionToken, Integer quantity, User user) {

        Bucket bucket = new Bucket();
        BucketItem bucketItem = new BucketItem();
        bucketItem.setQuantity(quantity);
        bucketItem.setDate(new Date());
        bucketItem.setProduct(productService.getById(id));

        bucket.getBucketItems().add(bucketItem);
        bucket.setSessionToken(sessionToken);
        bucket.setTotalItems(bucketItem.getQuantity());
        bucket.setTotalPrice(bucketItem.getProduct().getPrice());
        bucket.setDate(new Date());
        bucket.setUser(user);

        return bucketRepository.save(bucket);
    }

    public Bucket removeItemFromBucket(Product product, User user) {
        Bucket bucket = user.getBucket();
        Set<BucketItem> bucketItems = bucket.getBucketItems();
        BucketItem item = findBucketItem(bucketItems, product.getId());
        bucketItems.remove(item);
        BigDecimal totalPrice = totalPrice(bucketItems);
        int totalItems = totalItems(bucketItems);


        bucket.setBucketItems(bucketItems);
        bucket.setTotalPrice(totalPrice);
        bucket.setTotalItems(totalItems);

        return bucketRepository.save(bucket);
    }

    private BucketItem findBucketItem(Set<BucketItem> bucketItems, Long productId) {
        if (bucketItems == null) {
            return null;
        }
        BucketItem bucketItem = null;

        for (BucketItem item : bucketItems) {
            if (item.getProduct().getId() == productId) {
                bucketItem = item;
            }
        }
        return bucketItem;
    }

    private int totalItems(Set<BucketItem> bucketItems){
        int totalItems = 0;;
        for(BucketItem item : bucketItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private BigDecimal totalPrice(Set<BucketItem> bucketItems){
        BigDecimal totalPrice = BigDecimal.ZERO;;

        for(BucketItem item : bucketItems){
            totalPrice =  (item.getProduct().getPrice()).add(totalPrice);
        }
        return totalPrice;
    }



//    @Override
//    public Bucket getBucketBySessionToken(String sessionToken) {
//        return bucketRepository.findBySessionToken(sessionToken);
//    }

    @Override
    public Bucket addToExistingBucket(Long id, String sessionToken, Integer quantity, User user) {
        Bucket bucket = bucketRepository.findBySessionToken(sessionToken);
        Product product = productService.getById(id);

        Boolean productDoesExistInTheCart = false;
        if ((bucket != null) && user.equals(bucket.getUser())) {
            Set<BucketItem> bucketItems = bucket.getBucketItems();
            for (BucketItem item : bucketItems) {
                if (item.getProduct().equals(product)) {
                    productDoesExistInTheCart = true;
                    item.setQuantity(item.getQuantity() + quantity);
                    bucket.setBucketItems(bucketItems);
                    return bucketRepository.saveAndFlush(bucket);
                }

            }
        }
        if(!productDoesExistInTheCart && (bucket != null))
        {
            BucketItem newBucketItems = new BucketItem();
            newBucketItems.setQuantity(quantity);
            newBucketItems.setProduct(product);
            bucket.getBucketItems().add(newBucketItems);
            return bucketRepository.saveAndFlush(bucket);
        }

        return this.addItemToCart(id, sessionToken, quantity, user);
    }
    @Override
    public Bucket getBucketBySessionToken(String sessionToken) {

        return  bucketRepository.findBySessionToken(sessionToken);
    }
    @Override
    public BucketItem updateItemInCart(Long id, Integer quantity) {
        BucketItem bucketItem = bucketItemRepository.findById(id).get();
        bucketItem.setQuantity(quantity);
        return bucketItemRepository.saveAndFlush(bucketItem);
    }




    @Override
    public Bucket removeBucketItemFromBucket(Long id, String sessionToken) {
        Bucket bucket = bucketRepository.findBySessionToken(sessionToken);
        Set<BucketItem> items = bucket.getBucketItems();
        BucketItem bucketItem = null;
        for(BucketItem item : items) {
            if(item.getId()==id) {
                bucketItem = item;
            }
        }
        items.remove(bucketItem);
        bucketItemRepository.delete(bucketItem);
        bucket.setBucketItems(items);
        return bucketRepository.save(bucket);
    }

    @Override
    public void clearBucket(String sessionToken) {
            Bucket bucket = bucketRepository.findBySessionToken(sessionToken);
            bucketRepository.delete(bucket);
        }

}
