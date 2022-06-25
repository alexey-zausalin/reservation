package com.github.alexeyzausalin.reservation.repository;

import com.github.alexeyzausalin.reservation.domain.dto.Page;
import com.github.alexeyzausalin.reservation.domain.dto.SearchFacilitiesQuery;
import com.github.alexeyzausalin.reservation.domain.exception.NotFoundException;
import com.github.alexeyzausalin.reservation.domain.model.Facility;
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

@Repository
public interface FacilityRepository extends MongoRepository<Facility, ObjectId>, FacilityRepositoryCustom {
    default Facility getById(ObjectId id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Facility.class, id));
    }

    List<Facility> findAllById(Iterable<ObjectId> ids);
}

interface FacilityRepositoryCustom {

    List<Facility> searchFacilities(Page page, SearchFacilitiesQuery query);

}

@RequiredArgsConstructor
class FacilityRepositoryCustomImpl implements FacilityRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Facility> searchFacilities(Page page, SearchFacilitiesQuery query) {
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
        if (StringUtils.hasText(query.group())) {
            criteriaList.add(Criteria.where("group").is(query.group()));
        }
        if (StringUtils.hasText(query.name())) {
            criteriaList.add(Criteria.where("name").regex(query.name(), "i"));
        }
        if (!criteriaList.isEmpty()) {
            Criteria facilityCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            operations.add(match(facilityCriteria));
        }

        operations.add(sort(Sort.Direction.DESC, "createdAt"));
        operations.add(skip((page.number() - 1) * page.limit()));
        operations.add(limit(page.limit()));

        TypedAggregation<Facility> aggregation = newAggregation(Facility.class, operations);
        System.out.println(aggregation.toString());
        AggregationResults<Facility> results = mongoTemplate.aggregate(aggregation, Facility.class);
        return results.getMappedResults();
    }
}