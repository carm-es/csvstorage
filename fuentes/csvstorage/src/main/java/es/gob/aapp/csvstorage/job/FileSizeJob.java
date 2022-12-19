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

package es.gob.aapp.csvstorage.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import es.gob.aapp.csvstorage.services.manager.document.DocumentManagerService;
import es.gob.aapp.csvstorage.util.file.FileUtil;

public class FileSizeJob extends QuartzJobBean {

  private static Logger logger = LogManager.getLogger(FileSizeJob.class);

  @Autowired
  private DocumentManagerService documentManagerService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    logger.info("[INI] executeInternal." + FileUtil.getFdOpen());
    try {

      documentManagerService.updateFileSize();

    } catch (es.gob.aapp.csvstorage.services.exception.ServiceException e) {
      logger.error("OrganicUnitJob-executeInternal error al cargar datos: " + e.getMessage(), e);
    }
    logger.info("[FIN] executeInternal." + FileUtil.getFdOpen());
  }

}
