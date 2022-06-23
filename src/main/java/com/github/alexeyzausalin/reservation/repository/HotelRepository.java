package com.github.alexeyzausalin.reservation.repository;

import com.github.alexeyzausalin.reservation.domain.dto.Page;
import com.github.alexeyzausalin.reservation.domain.dto.SearchHotelsQuery;
import com.github.alexeyzausalin.reservation.domain.exception.NotFoundException;
import com.github.alexeyzausalin.reservation.domain.model.Hotel;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, ObjectId>, HotelRepositoryCustom {

    default Hotel getById(ObjectId id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Hotel.class, id));
    }

    List<Hotel> findAllById(Iterable<ObjectId> ids);
}

interface HotelRepositoryCustom {

    List<Hotel> searchHotels(Page page, SearchHotelsQuery query);

}

@RequiredArgsConstructor
class HotelRepositoryCustomImpl implements HotelRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Hotel> searchHotels(Page page, SearchHotelsQuery query) {
        List<AggregationOperation> operations = new ArrayList<>();

        List<Criteria> criteriaList = new ArrayList<>();
        if (StringUtils.hasText(query.id())) {
            criteriaList.add(Criteria.where("id").is(new ObjectId(query.id())));
        }
        if (query.createdAtStart() != null) {
            criteriaList.add(Criteria.where("createdAt").gte(query.createdAtStart()));
        }
        if (query.createdAtEnd() != null) {
            criteriaList.add(Criteria.where("createdAt").lt(query.createdAtEnd()));
        }
        if (StringUtils.hasText(query.name())) {
            criteriaList.add(Criteria.where("name").regex(query.name(), "i"));
        }
        if (StringUtils.hasText(query.hotelType())) {
            criteriaList.add(Criteria.where("hotelType").is(query.hotelType()));
        }
        if (!criteriaList.isEmpty()) {
            Criteria hotelCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            operations.add(match(hotelCriteria));
        }

        operations.add(sort(Sort.Direction.DESC, "createdAt"));
        operations.add(skip((page.number() - 1) * page.limit()));
        operations.add(limit(page.limit()));

        TypedAggregation<Hotel> aggregation = newAggregation(Hotel.class, operations);
        System.out.println(aggregation.toString());
        AggregationResults<Hotel> results = mongoTemplate.aggregate(aggregation, Hotel.class);
        return results.getMappedResults();
    }

}
