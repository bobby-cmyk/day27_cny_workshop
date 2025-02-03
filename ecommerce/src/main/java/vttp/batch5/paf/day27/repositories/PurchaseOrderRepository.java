package vttp.batch5.paf.day27.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.day27.models.LineItem;
import vttp.batch5.paf.day27.models.PurchaseOrder;

@Repository
public class PurchaseOrderRepository {
    
    @Autowired
    private JdbcTemplate sqlTemplate;

    public boolean addPurchaseOrder(PurchaseOrder po) {
        String SQL_INSERT_PO = """
            INSERT INTO purchase_orders(po_id, name, address, delivery_date)
            VALUES (?, ?, ?, ?);      
        """;

        int addedPo = sqlTemplate.update(SQL_INSERT_PO, po.getPoId(), po.getName(), po.getAddress(), po.getDeliveryDate());

        boolean addedLi = addLineItems(po.getLineItems());

        // If both successful
        return (addedPo > 0) && addedLi;
    }

    private boolean addLineItems(List<LineItem> lineItems) {
        String SQL_INSERT_LI = """
            INSERT INTO line_items(name, quantity, unit_price, po_id) 
            VALUES (?, ?, ?, ?)
        """;

        List<Object[]> params = lineItems.stream()
            .map(li -> new Object[]{ li.getName(), li.getQuantity(), li.getUnitPrice(), li.getPoId()})
            .collect(Collectors.toList());

        int addedLi[] = sqlTemplate.batchUpdate(SQL_INSERT_LI, params);

        return addedLi.length > 0;
    }
}