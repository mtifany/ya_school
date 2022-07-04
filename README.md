# ya_school

Задание для отбора в летнюю школу Яндекс

Задача - разработать REST API сервис, который позволяет магазинам загружать и обновлять информацию о товарах.

 /imports:
        Импортирует новые товары и/или категории. Товары/категории импортированные повторно обновляют текущие.
        Изменение типа элемента с товара на категорию или с категории на товар не допускается. 
        Порядок элементов в запросе является произвольным.

/delete/{id}:
        Удаляет элемент по идентификатору. При удалении категории удаляются все дочерние элементы. 
        Доступ к статистике (истории обновлений) удаленного элемента невозможен.

nodes/{id}:
        Получает информацию об элементе по идентификатору. 
        При получении информации о категории также предоставляется информация о её дочерних элементах.
