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

package es.gob.aapp.csvstorage.util;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.itextpdf.text.Element;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEntity;
import es.gob.aapp.csvstorage.util.constants.Errors;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;

@Component("pdfUtils")
public class PdfUtils {

  /**
   * * The Constant DEFAULT_FONT_BOLD.
   */
  public static final Font DEFAULT_FONT_BOLD = new Font(Font.HELVETICA, 8, Font.BOLD);
  /**
   * The Constant DEFAULT_FONT_BASE.
   */
  public static final Font DEFAULT_FONT_BASE = new Font(Font.HELVETICA, 12);
  /**
   * The Constant DEFAULT_FONT_BASE_INDEX.
   */
  public static final Font DEFAULT_FONT_BASE_INDEX = new Font(Font.HELVETICA, 8);
  /**
   * The Constant DEFAULT_WIDTH_PERCENTAGE.
   */
  private static final float DEFAULT_WIDTH_PERCENTAGE = 100f;
  /**
   * The Constant DEFAULT_BACKGROUND_TITLE.
   */
  private static final Color DEFAULT_BACKGROUND_TITLE = new Color(200, 200, 200);
  /**
   * The Constant DEFAULT_FONT_TITLE.
   */
  private static final Font DEFAULT_FONT_TITLE = new Font(Font.HELVETICA, 12, Font.BOLD);
  /**
   * The Constant DEFAULT_ALIGNMENT_TITLE.
   */
  private static final int DEFAULT_ALIGNMENT_TITLE = Element.ALIGN_CENTER;
  /**
   * The Constant WIDTH_TABLE_HOR.
   */
  private static final float WIDTH_TABLE_HOR = 727f;
  /**
   * The Constant WIDTH_TABLE_VER.
   */
  private static final float WIDTH_TABLE_VER = 480f;
  /**
   * The Constant relativeWidthsHor.
   */
  private static final float[] relativeWidthsHor = {1f};
  /**
   * The Constant relativeWidthsVer.
   */
  private static final float[] relativeWidthsVer = {1f};

  /**
   * The context.
   */
  @Autowired(required = true)
  private ApplicationContext applicationContext;

  public byte[] addCabecera(byte[] pdfEntrada, Document doc) throws CSVStorageException {

    byte[] bytesSalida = null;

    ByteArrayInputStream bis = new ByteArrayInputStream(pdfEntrada);
    ByteArrayOutputStream salida = new ByteArrayOutputStream();
    try {
      PdfReader reader = new PdfReader(bis);
      salida = new ByteArrayOutputStream();
      PdfStamper stamp = new PdfStamper(reader, salida);
      stamp.setFormFlattening(false);
      int n = reader.getNumberOfPages();

      for (int i = 1; i <= n; i++) {

        PdfPTable cabecera;
        if (stamp.getImportedPage(reader, i).getHeight() > stamp.getImportedPage(reader, i)
            .getWidth()) {
          // Vertical
          cabecera = designTable(WIDTH_TABLE_VER, relativeWidthsVer);
        } else {
          // Horizontal
          cabecera = designTable(WIDTH_TABLE_HOR, relativeWidthsHor);
        }

        cabecera.writeSelectedRows(0, -1, doc.leftMargin(),
            stamp.getImportedPage(reader, i).getHeight() - 15, stamp.getOverContent(i));
      }

      reader.close();
      salida.close();
      stamp.close();

      bytesSalida = salida.toByteArray();

    } catch (Exception e) {
      throw new CSVStorageException(Errors.INTERNAL_SERVICE_ERROR.getDescription(), e);
    }

    return bytesSalida;
  }

  /**
   * Design table.
   *
   * @param widthTabla the width tabla
   * @param relativeWidths the relative widths
   * @return the pdf p table
   * @throws UtilException the util exception
   */
  private PdfPTable designTable(float widthTabla, float[] relativeWidths)
      throws CSVStorageException {
    PdfPTable tabla = new PdfPTable(1);
    tabla.setTotalWidth(widthTabla);
    try {
      tabla.setWidths(relativeWidths);
    } catch (DocumentException e) {

      throw new CSVStorageException("No se puede dibujar la tabla ", e);
    }

    Image img = null;
    try {
      img = Image.getInstance(
          applicationContext.getResource("classpath:static/public/images/escudo.png").getURL());
    } catch (Exception e) {

      throw new CSVStorageException(
          "No se puede obtener la imagen a partir de: /static/public/images/escudo.png", e);
    }
    tabla.addCell(celdaLogo(img));

    return tabla;
  }

  /**
   * Celda logo.
   *
   * @param img the img
   * @return the pdf p cell
   */
  private PdfPCell celdaLogo(Image img) {
    PdfPCell cellImg = new PdfPCell();
    cellImg.setBorder(PdfPCell.NO_BORDER);
    img.scalePercent(25);
    cellImg.addElement(img);
    return cellImg;
  }

  /**
   * Creates the document.
   *
   * @return the document
   */
  private Document createDocument() {
    Document doc = new Document();
    doc.setPageSize(new Rectangle(842, 595));
    doc.setMargins(75, 40, 100, 60);
    return doc;
  }

  /**
   * Prints the title.
   *
   * @param doc the doc
   * @param title the title
   * @throws DocumentException the document exception
   */
  private void printTitle(Document doc, String title) throws com.lowagie.text.DocumentException {
    PdfPTable table = new PdfPTable(1);
    table.setWidthPercentage(DEFAULT_WIDTH_PERCENTAGE);

    PdfPCell cell = new PdfPCell();
    cell.setBorderWidth(0.5f);
    cell.setBackgroundColor(DEFAULT_BACKGROUND_TITLE);
    cell.setPaddingTop(-3f);
    cell.setPaddingLeft(0f);

    Paragraph p = new Paragraph(title, DEFAULT_FONT_TITLE);
    p.setAlignment(DEFAULT_ALIGNMENT_TITLE);
    cell.addElement(p);

    table.addCell(cell);
    table.setSpacingAfter(10f);

    try {
      doc.add(table);
      doc.add(Chunk.NEWLINE);
    } catch (com.lowagie.text.DocumentException e) {
      throw e;
    }
  }

  /**
   * Fill pdf.
   *
   * @param dataPDF the data pdf
   * @param data the data
   * @param generalData the general data
   * @return the pdf p table
   */
  private PdfPTable fillPdf(Map<String, String> dataPDF, Map<String, String> data,
      PdfPTable generalData) {
    for (Map.Entry<String, String> entry : dataPDF.entrySet()) {
      generalData.addCell(createCellClave(entry.getKey(), Element.ALIGN_LEFT, PdfPCell.NO_BORDER,
          DEFAULT_FONT_BOLD, -1));
      generalData.addCell(createCellValue(data.get(entry.getValue()), Element.ALIGN_LEFT,
          PdfPCell.NO_BORDER, DEFAULT_FONT_BASE, -1));
      generalData.setSpacingAfter(30f);
    }

    return generalData;
  }

  /**
   * Creates the cell clave.
   *
   * @param texto the texto
   * @param alignment the alignment
   * @param border the border
   * @param font the font
   * @param profundidad the profundidad
   * @return the pdf p cell
   */
  private PdfPCell createCellClave(String texto, int alignment, int border, Font font,
      int profundidad) {
    PdfPCell celdaClave = new PdfPCell();
    celdaClave.setBorder(border);
    if (profundidad != -1) {
      celdaClave.setPaddingLeft(8f * profundidad);
    } else {
      celdaClave.setPaddingLeft(4f);
    }
    celdaClave.setPaddingTop(-1f);
    Paragraph p = new Paragraph(texto, font);
    p.setAlignment(alignment);
    celdaClave.addElement(p);

    return celdaClave;
  }

  /**
   * Creates the cell value.
   *
   * @param texto the texto
   * @param alignment the alignment
   * @param border the border
   * @param font the font
   * @param profundidad the profundidad
   * @return the pdf p cell
   */
  private PdfPCell createCellValue(String texto, int alignment, int border, Font font,
      int profundidad) {
    PdfPCell celdaValor = new PdfPCell();
    celdaValor.setBorder(border);
    celdaValor.setPaddingTop(-2f);
    if (profundidad != -1) {
      celdaValor.setPaddingLeft(8f * profundidad);
    } else {
      celdaValor.setPaddingLeft(4f);
    }
    Paragraph p1 = new Paragraph(texto, font);
    p1.setAlignment(alignment);
    celdaValor.addElement(p1);
    return celdaValor;
  }

  /**
   * Prints the general data acta de traspaso.
   *
   * @param data the data
   * @param doc the doc
   * @param metadatosOrgano the metadatos organo
   * @throws DocumentException the document exception
   */
  private void printGeneralDataActaDeTraspaso(Document doc, Map<String, String> data,
      DocumentEntity documentEntity) throws DocumentException {
    PdfPTable generalData = new PdfPTable(2);
    generalData.setWidthPercentage(DEFAULT_WIDTH_PERCENTAGE);
    generalData.setWidths(new float[] {0.25f, 0.75f});

    Map<String, String> dataPdf = new HashMap<String, String>();

    dataPdf.put("Identificador", "identificador");

    dataPdf.put("CSV", "csv");

    dataPdf.put("Fecha", "fecha");

    doc.add(fillPdf(dataPdf, data, generalData));
  }

  public byte[] createPdfConsultaPorCSV(DocumentEntity documentEntity) throws CSVStorageException {
    try {

      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

      HashMap<String, String> data = new HashMap<String, String>();
      data.put("identificador", documentEntity.getIdEni());
      data.put("csv", documentEntity.getCsv());
      data.put("fecha",
          documentEntity.getCreatedAt() != null ? format.format(documentEntity.getCreatedAt())
              : "");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      // creamos el pdf
      Document doc = createDocument();

      PdfWriter pw = PdfWriter.getInstance(doc, baos);
      pw.setLinearPageMode();
      doc.open();

      doc.newPage();
      printTitle(doc, "Certificado de Almacenamiento");
      printGeneralDataActaDeTraspaso(doc, data, documentEntity);
      doc.close();
      pw.close();

      // añadido de cabecera
      return addCabecera(baos.toByteArray(), doc);

    } catch (DocumentException e) {

      throw new CSVStorageException(Errors.INTERNAL_SERVICE_ERROR.getDescription(), e);
    }
  }

}
