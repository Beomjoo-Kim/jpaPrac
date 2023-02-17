package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    //class 에 지정한 @Transactional 의 설정값이 readOnly=true 이기 때문에 따로 지정해 주어야 한다.
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, UpdateItemDto updateItemDto) {
        //이 method 는 entityManager.merge 의 내부 로직과 비슷하다.

        //영속성 엔티티
        /*Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());*/
        //영속성 엔티티이므로 save 호출이 불필요하다.
        //transaction 이 종료되며 flush 가 호출되고, 이 때 변경감지에 의해 db 가 update 된다.
//        itemRepository.save(findItem);

        //해당 method 처럼 하나하나 set 하기 보다는 entity 에서 의미있는 변경이 되도록 코드를 작성해야한다.
        //그리해야 역추적에 용이하다.
        //ex)Item.addStock();

        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(updateItemDto.getName());
        findItem.setPrice(updateItemDto.getPrice());
        findItem.setStockQuantity(updateItemDto.getStockQuantity());
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

}
