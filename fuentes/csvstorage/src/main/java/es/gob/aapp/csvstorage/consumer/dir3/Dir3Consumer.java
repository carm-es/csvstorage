/*
 * Copyright (C) 2012-13 MINHAP, Gobierno de España This program is licensed and may be used,
 * modified and redistributed under the terms of the European Public License (EUPL), either version
 * 1.1 or (at your option) any later version as soon as they are approved by the European
 * Commission. Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * more details. You should have received a copy of the EUPL1.1 license along with this program; if
 * not, you may find it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.gob.aapp.csvstorage.consumer.dir3;

import es.gob.aapp.csvstorage.consumer.dir3.model.Unidades;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;

/**
 * Clase consumidora de los servicios web de DIR3.
 * 
 */

public interface Dir3Consumer {

  /**
   * Servicio consumidor del web service SD01UN_VOLCADODATOSBASICO E
   * SC02UN_INCREMENTAL_DATOS_BASICOS del DIR3 por el que se descarga y descomprime un ZIP con el
   * archivo XML de unidades org�nicas
   *
   * @return the unidades
   * @throws ConsumerWSException the consumer ws exception
   */
  public Unidades exportOrganicUnitsToXML() throws ConsumerWSException;

  public Unidades exportAllOrganicUnitsToXML() throws ConsumerWSException;

  public Unidades exportOrganicUnitsToXML(String fecha) throws ConsumerWSException;



}
