package org.example.web.mapper;

import org.example.model.Data;
import org.example.web.DTO.DataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
//Mapper - анотація, що вказує, які класи слід використовувати для генерації маперів.
//За допомогою цієї анотації MapStruct може генерувати код для методів маппінгу на основі зазначених інтерфейсів

//componentModel = "spring" - параметр, який вказує,
// що генеровані мапери можуть бути інтегровані з Spring як керовані бінами(managed beans).
// Що означає, що ці мапери можна використовувати в інших компонентах додатку,
// і Spring буде автоматично керувати їхнім життєвим циклом

//MapStruct - фреймворк для генерації коду маперів в Java.
//Він дозволяє автоматично створювати код для взаємного перетворення об'єктів одного класу в інший
public interface DataMapper extends Mappable<Data, DataDTO> {
}
