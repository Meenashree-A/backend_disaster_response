package com.meenashree.disasterresponse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.meenashree.disasterresponse.model.Need;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

@Service
public class DynamoDbService {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    public DynamoDbService(DynamoDbClient dynamoDbClient, @Value("${aws.dynamodb.table}") String tableName) {
        this.dynamoDbClient = dynamoDbClient;
        this.tableName = tableName;
    }

    // Updated method to save Need with phone
    public void saveNeed(Need need) {
        String id = UUID.randomUUID().toString();
        need.setId(id);

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(need.getId()).build());
        item.put("location", AttributeValue.builder().s(need.getLocation()).build());

        // Check if phone is not null or empty before adding
        if (need.getPhone() != null && !need.getPhone().isEmpty()) {
            item.put("phone", AttributeValue.builder().s(need.getPhone()).build());
        }

        item.put("type", AttributeValue.builder().s(need.getType()).build());
        item.put("description", AttributeValue.builder().s(need.getDescription()).build());
        item.put("fileUrl", AttributeValue.builder().s(need.getFileUrl()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }

    // Updated method to retrieve all Needs with safe phone handling
    public List<Need> getAllNeeds() {
        List<Need> needsList = new ArrayList<>();

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);

        for (Map<String, AttributeValue> item : response.items()) {
            Need need = new Need();
            need.setId(item.get("id").s());
            need.setLocation(item.get("location").s());
            // Safe check for 'phone' key existence
            need.setPhone(item.containsKey("phone") ? item.get("phone").s() : "");
            need.setType(item.get("type").s());
            need.setDescription(item.get("description").s());
            need.setFileUrl(item.get("fileUrl").s());
            needsList.add(need);
        }

        return needsList;
    }
}
