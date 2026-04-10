package com.project.code.Repo;

import com.project.code.Model.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository  extends JpaRepository<Inventory,Long> {

    public Inventory findByProductIdAndStoreId(Long productId, Long storeId);
    public List<Inventory> findByStore_Id(Long id);
    @Transactional //accordig to Spring JPA's  Transactionality doc, read-only methods don't need de Transactional annotation so this Delete methods is the only one that requires so
    @Modifying //important for methods that modify the DB (in other words, they are not read-only). Otherwise, Transactional's  parameter can be set to readOnly = true, it's not necessary tho unless specifically modifying default behavior
    public void deleteByProductId(Long id);



}


