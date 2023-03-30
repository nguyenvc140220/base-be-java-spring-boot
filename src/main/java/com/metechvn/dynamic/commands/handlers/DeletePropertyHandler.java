package com.metechvn.dynamic.commands.handlers;

import com.metechvn.dynamic.commands.DeletePropertyCommand;
import com.metechvn.dynamic.entities.DynamicProperty;
import com.metechvn.dynamic.repositories.DynamicPropertyRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePropertyHandler implements RequestHandler<DynamicProperty, DeletePropertyCommand> {

    private final DynamicPropertyRepository dynamicPropertyRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public DynamicProperty handle(DeletePropertyCommand cmd) {
        var existing = dynamicPropertyRepository.findByCode(cmd.getCode());
        if (existing == null)
            throw new BusinessException(String.format("Mã %s không tồn tại", cmd.getCode()));
        var countValue = dynamicPropertyRepository.countValueById(existing.getId());
        if (countValue == 0) {
            existing.setDeleted(true);
            dynamicPropertyRepository.save(existing);
        } else {
            throw new BusinessException("Không thể xóa trường động đã được gán giá trị");
        }

        return existing;
    }
}
