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

package es.gob.aapp.csvstorage.webservices.documentmtom.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.documentoe.mtom.TipoDocumentoMtom;
import es.gob.aapp.csvstorage.webservices.document.model.Response;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoEniMtomResponse", propOrder = {"documento"},
    namespace = "urn:es:gob:aapp:csvstorage:webservices:documentmtom:model:v1.0")
public class DocumentoEniMtomResponse extends Response {

  protected TipoDocumentoMtom documento;

  /**
   * @return the documento
   */
  public TipoDocumentoMtom getDocumento() {
    return documento;
  }

  /**
   * @param documento the documento to set
   */
  public void setDocumento(TipoDocumentoMtom documento) {
    this.documento = documento;
  }


}
