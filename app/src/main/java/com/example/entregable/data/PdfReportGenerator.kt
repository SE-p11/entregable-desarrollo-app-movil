package com.example.entregable.data

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import com.example.entregable.model.Product
import java.io.File
import java.io.FileOutputStream

class PdfReportGenerator(private val context: Context) {

    fun generatePdfReport(products: List<Product>) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 Size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint()

        paint.textSize = 20f
        paint.isFakeBoldText = true
        canvas.drawText("Reporte de Inventario de Productos", 40f, 50f, paint)

        paint.textSize = 14f
        paint.isFakeBoldText = false
        var yPosition = 100f

        canvas.drawText("ID", 40f, yPosition, paint)
        canvas.drawText("Nombre", 100f, yPosition, paint)
        canvas.drawText("Precio", 300f, yPosition, paint)
        canvas.drawText("Cantidad", 420f, yPosition, paint)

        yPosition += 20f
        canvas.drawLine(40f, yPosition, 550f, yPosition, paint)

        yPosition += 30f
        for (product in products) {
            canvas.drawText(product.id.toString(), 40f, yPosition, paint)
            canvas.drawText(product.name, 100f, yPosition, paint)
            canvas.drawText("S/ ${product.price}", 300f, yPosition, paint)
            canvas.drawText(product.quantity.toString(), 420f, yPosition, paint)
            yPosition += 25f
        }

        pdfDocument.finishPage(page)

        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Reporte_Inventario.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "PDF guardado en: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al generar PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }
}