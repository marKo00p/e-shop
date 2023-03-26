package com.shopy.trainshop.service;

import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.BucketItem;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.dto.BucketDTO;

import java.util.List;
import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.dto.UserDTO;

public interface BucketService {

     Bucket addToExistingBucket(Long id, String sessionToken, Integer quantity, User user);
     Bucket getBucketBySessionToken(String sessionToken);
     Bucket addItemToCart(Long id, String sessionToken, Integer quantity, User user);
    BucketItem updateItemInCart(Long id, Integer quantity);
    Bucket removeBucketItemFromBucket(Long id,String sessionToken);
    void clearBucket(String sessionToken);


}
