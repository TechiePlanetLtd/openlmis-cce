/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.cce.web.csv.recordhandler;

import org.openlmis.cce.domain.BaseEntity;
import org.openlmis.cce.domain.CatalogItem;
import org.openlmis.cce.repository.CatalogItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for saving {@link CatalogItem} instances to the database.
 */
@Component
public class CatalogItemWriter extends AbstractRecordWriter<CatalogItem> {

  @Autowired
  private CatalogItemRepository catalogItemRepository;

  @Override
  protected BaseEntity getExisting(CatalogItem record) {
    String equipmentCode = record.getEquipmentCode();

    if (null != equipmentCode
        && catalogItemRepository.existsByEquipmentCode(equipmentCode)) {
      return catalogItemRepository.findByEquipmentCode(equipmentCode);
    }

    String manufacturer = record.getManufacturer();
    String model = record.getModel();

    if (catalogItemRepository.existsByManufacturerAndModel(manufacturer, model)) {
      return catalogItemRepository.findByManufacturerAndModel(manufacturer, model);
    }

    return null;
  }

  @Override
  protected void save(Iterable<CatalogItem> records) {
    catalogItemRepository.save(records);
  }
}
