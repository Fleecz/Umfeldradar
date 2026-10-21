package de.umfeldradar.thema;

import org.springframework.data.jpa.domain.Specification;

public class ThemaSpecifications {
    static Specification<Thema> hatPestel(Pestel pestel){
        return (root, query, cb) ->
                cb.equal(root.get("pestel"), pestel);
    }
    static Specification<Thema> hatHerkunft(Herkunft herkunft){
        return (root, query, cb) ->
                cb.equal(root.get("herkunft"), herkunft);
    }
    static Specification<Thema> hatStatus(ThemaStatus status){
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }
    static Specification<Thema> titelEnthaelt(String suchtext){
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("titel")),
                        "%" + suchtext.toLowerCase() + "%");
    }
}