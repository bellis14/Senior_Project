/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo.kotlin.facedetector

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.google.mlkit.vision.demo.GraphicOverlay
import com.google.mlkit.vision.demo.GraphicOverlay.Graphic
import com.google.mlkit.vision.demo.kotlin.LivePreviewActivity
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceLandmark
import com.google.mlkit.vision.face.FaceLandmark.LandmarkType
import java.io.IOException
import java.util.Locale
import kotlin.math.abs
import kotlin.math.max

/**
 * Graphic instance for rendering face position, contour, and landmarks within the associated
 * graphic overlay view.
 */
class FaceGraphic constructor(overlay: GraphicOverlay?, private val face: Face) : Graphic(overlay) {
  private val facePositionPaint: Paint
  private val numColors = COLORS.size
  private val idPaints = Array(numColors) { Paint() }
  private val boxPaints = Array(numColors) { Paint() }
  private val labelPaints = Array(numColors) { Paint() }

  init {
    val selectedColor = Color.WHITE
    facePositionPaint = Paint()
    facePositionPaint.color = selectedColor
    for (i in 0 until numColors) {
      idPaints[i] = Paint()
      idPaints[i].color = COLORS[i][0]
      idPaints[i].textSize = ID_TEXT_SIZE
      boxPaints[i] = Paint()
      boxPaints[i].color = COLORS[i][1]
      boxPaints[i].style = Paint.Style.STROKE
      boxPaints[i].strokeWidth = BOX_STROKE_WIDTH
      labelPaints[i] = Paint()
      labelPaints[i].color = COLORS[i][1]
      labelPaints[i].style = Paint.Style.FILL
    }
  }

  private fun sendCommand(input: String) {
    if (LivePreviewActivity.m_bluetoothSocket != null) {
      try{
        LivePreviewActivity.m_bluetoothSocket!!.outputStream.write(input.toByteArray())
      } catch(e: IOException) {
        e.printStackTrace()
      }
    }
  }

  /** Draws the face annotations for position on the supplied canvas. */
  override fun draw(canvas: Canvas) {

    val detectedImagePosXdraw = translateX(face.boundingBox.centerX().toFloat())
    val detectedImagePosYdraw = translateY(face.boundingBox.centerY().toFloat())

    val detectedImagePosX = face.boundingBox.centerX().toFloat()
    val detectedImagePosY = face.boundingBox.centerY().toFloat()


    val frameCenterX = (canvas.width / 2).toFloat()
    val frameCenterY = (canvas.height / 2).toFloat()

    // Draw green dot in the center of the face detected
    val faceColor = Color.GREEN
    val faceCenterPosColor = Paint()
    faceCenterPosColor.color = faceColor
    canvas.drawCircle(detectedImagePosXdraw, detectedImagePosYdraw, 5.0f, faceCenterPosColor)

    // Draw red dot in the center of the frame
    val frameCenterColor = Paint()
    frameCenterColor.color = Color.RED
    canvas.drawCircle(frameCenterX, frameCenterY, 5.0f, frameCenterColor)

    // Draw a green line connecting the center of the face and the center of the frame
    canvas.drawLine(detectedImagePosXdraw, detectedImagePosYdraw, frameCenterX, frameCenterY, faceCenterPosColor)

    // Compute the x and y difference and tell motors where to move
    val displacementX = abs(detectedImagePosX - frameCenterX)
    val displacementY = abs(detectedImagePosY - frameCenterY)


    // Perform a PAN
      // Tilt Left
    if (detectedImagePosXdraw < frameCenterX-X_BUFF) {
      if (isImageFlipped)
        sendCommand(PAN_ANTI_CLOCKWISE)
      else {
        sendCommand(PAN_CLOCKWISE) // pan clockwise (right)
        Log.d("pan1", "pan clockwise - $detectedImagePosXdraw")
      }
    }
      // Tilt Right
    else if (detectedImagePosXdraw > frameCenterX+X_BUFF) {
      if (isImageFlipped)
        sendCommand(PAN_CLOCKWISE)
      else {
        sendCommand(PAN_ANTI_CLOCKWISE)
        Log.d("pan2", "pan counterclockwise - $detectedImagePosXdraw")
      }
    }


    // Perform a TILT
      // Tilt Down
    if (detectedImagePosYdraw < frameCenterY-Y_BUFF) {
      if (isImageFlipped)
        sendCommand(TILT_CLOCKWISE)
      else
        sendCommand(TILT_ANTI_CLOCKWISE)
    }
      // Tilt Up
    else if (detectedImagePosYdraw > frameCenterY+Y_BUFF) {
      if (isImageFlipped)
        sendCommand(TILT_ANTI_CLOCKWISE)
      else
        sendCommand(TILT_CLOCKWISE)
    }



    // Calculate positions.
    val left = detectedImagePosXdraw - scale(face.boundingBox.width() / 2.0f)
    val top = detectedImagePosYdraw - scale(face.boundingBox.height() / 2.0f)
    val right = detectedImagePosXdraw + scale(face.boundingBox.width() / 2.0f)
    val bottom = detectedImagePosYdraw + scale(face.boundingBox.height() / 2.0f)
    val lineHeight = ID_TEXT_SIZE + BOX_STROKE_WIDTH
    var yLabelOffset: Float = if (face.trackingId == null) 0f else -lineHeight

    // Decide color based on face ID
    val colorID = if (face.trackingId == null) 0 else abs(face.trackingId!! % NUM_COLORS)

    // Calculate width and height of label box
    var textWidth = idPaints[colorID].measureText("ID: " + face.trackingId)
    if (face.smilingProbability != null) {
      yLabelOffset -= lineHeight
      textWidth =
        max(
          textWidth,
          idPaints[colorID].measureText(
            String.format(Locale.US, "Hppineass: %.2f", face.smilingProbability)
          )
        )
    }
    if (face.leftEyeOpenProbability != null) {
      yLabelOffset -= lineHeight
      textWidth =
        max(
          textWidth,
          idPaints[colorID].measureText(
            String.format(Locale.US, "Left eye open: %.2f", face.leftEyeOpenProbability)
          )
        )
    }
    if (face.rightEyeOpenProbability != null) {
      yLabelOffset -= lineHeight
      textWidth =
        max(
          textWidth,
          idPaints[colorID].measureText(
            String.format(Locale.US, "Right eye open: %.2f", face.rightEyeOpenProbability)
          )
        )
    }

    yLabelOffset = yLabelOffset - 3 * lineHeight
    /*
    textWidth =
      Math.max(
        textWidth,
        idPaints[colorID].measureText(
          String.format(Locale.US, "EulerX: %.2f", face.headEulerAngleX)
        )
      )
    textWidth =
      Math.max(
        textWidth,
        idPaints[colorID].measureText(
          String.format(Locale.US, "EulerY: %.2f", face.headEulerAngleY)
        )
      )
    textWidth =
      Math.max(
        textWidth,
        idPaints[colorID].measureText(
          String.format(Locale.US, "EulerZ: %.2f", face.headEulerAngleZ)
        )
      )

    // Draw labels

    canvas.drawRect(
      left - BOX_STROKE_WIDTH,
      top + yLabelOffset,
      left + textWidth + 2 * BOX_STROKE_WIDTH,
      top,
      labelPaints[colorID]
    )
    */
    yLabelOffset += ID_TEXT_SIZE
    canvas.drawRect(left, top, right, bottom, boxPaints[colorID])
    if (face.trackingId != null) {
      canvas.drawText("ID: " + face.trackingId, left, top + yLabelOffset, idPaints[colorID])
      yLabelOffset += lineHeight
    }

    // Draws all face contours.
    for (contour in face.allContours) {
      for (point in contour.points) {
        canvas.drawCircle(
          translateX(point.x),
          translateY(point.y),
          FACE_POSITION_RADIUS,
          facePositionPaint
        )
      }
    }

    // Draws smiling and left/right eye open probabilities.
    if (face.smilingProbability != null) {
      canvas.drawText(
        "Smiling: " + String.format(Locale.US, "%.2f", face.smilingProbability),
        left,
        top + yLabelOffset,
        idPaints[colorID]
      )
      yLabelOffset += lineHeight
    }

    val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)
    if (face.leftEyeOpenProbability != null) {
      canvas.drawText(
        "Left eye open: " + String.format(Locale.US, "%.2f", face.leftEyeOpenProbability),
        left,
        top + yLabelOffset,
        idPaints[colorID]
      )
      yLabelOffset += lineHeight
    }
    if (leftEye != null) {
      val leftEyeLeft =
        translateX(leftEye.position.x) - idPaints[colorID].measureText("Left Eye") / 2.0f
      canvas.drawRect(
        leftEyeLeft - BOX_STROKE_WIDTH,
        translateY(leftEye.position.y) + ID_Y_OFFSET - ID_TEXT_SIZE,
        leftEyeLeft + idPaints[colorID].measureText("Left Eye") + BOX_STROKE_WIDTH,
        translateY(leftEye.position.y) + ID_Y_OFFSET + BOX_STROKE_WIDTH,
        labelPaints[colorID]
      )
      canvas.drawText(
        "Left Eye",
        leftEyeLeft,
        translateY(leftEye.position.y) + ID_Y_OFFSET,
        idPaints[colorID]
      )
    }

    val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)
    if (face.rightEyeOpenProbability != null) {
      canvas.drawText(
        "Right eye open: " + String.format(Locale.US, "%.2f", face.rightEyeOpenProbability),
        left,
        top + yLabelOffset,
        idPaints[colorID]
      )
      yLabelOffset += lineHeight
    }
    if (rightEye != null) {
      val rightEyeLeft =
        translateX(rightEye.position.x) - idPaints[colorID].measureText("Right Eye") / 2.0f
      canvas.drawRect(
        rightEyeLeft - BOX_STROKE_WIDTH,
        translateY(rightEye.position.y) + ID_Y_OFFSET - ID_TEXT_SIZE,
        rightEyeLeft + idPaints[colorID].measureText("Right Eye") + BOX_STROKE_WIDTH,
        translateY(rightEye.position.y) + ID_Y_OFFSET + BOX_STROKE_WIDTH,
        labelPaints[colorID]
      )
      canvas.drawText(
        "Right Eye",
        rightEyeLeft,
        translateY(rightEye.position.y) + ID_Y_OFFSET,
        idPaints[colorID]
      )
    }

    /*
    canvas.drawText("EulerX: " + face.headEulerAngleX, left, top + yLabelOffset, idPaints[colorID])
    yLabelOffset += lineHeight
    canvas.drawText("EulerY: " + face.headEulerAngleY, left, top + yLabelOffset, idPaints[colorID])
    yLabelOffset += lineHeight
    canvas.drawText("EulerZ: " + face.headEulerAngleZ, left, top + yLabelOffset, idPaints[colorID])
    */
    // Draw facial landmarks
    drawFaceLandmark(canvas, FaceLandmark.LEFT_EYE)
    drawFaceLandmark(canvas, FaceLandmark.RIGHT_EYE)
    drawFaceLandmark(canvas, FaceLandmark.LEFT_CHEEK)
    drawFaceLandmark(canvas, FaceLandmark.RIGHT_CHEEK)
  }

  private fun drawFaceLandmark(canvas: Canvas, @LandmarkType landmarkType: Int) {
    val faceLandmark = face.getLandmark(landmarkType)
    if (faceLandmark != null) {
      canvas.drawCircle(
        translateX(faceLandmark.position.x),
        translateY(faceLandmark.position.y),
        FACE_POSITION_RADIUS,
        facePositionPaint
      )
    }
  }

  companion object {
    private const val FACE_POSITION_RADIUS = 8.0f
    private const val ID_TEXT_SIZE = 30.0f
    private const val ID_Y_OFFSET = 40.0f
    private const val BOX_STROKE_WIDTH = 5.0f
    private const val NUM_COLORS = 10
    private val COLORS =
      arrayOf(
        intArrayOf(Color.BLACK, Color.WHITE),
        intArrayOf(Color.WHITE, Color.MAGENTA),
        intArrayOf(Color.BLACK, Color.LTGRAY),
        intArrayOf(Color.WHITE, Color.RED),
        intArrayOf(Color.WHITE, Color.BLUE),
        intArrayOf(Color.WHITE, Color.DKGRAY),
        intArrayOf(Color.BLACK, Color.CYAN),
        intArrayOf(Color.BLACK, Color.YELLOW),
        intArrayOf(Color.WHITE, Color.BLACK),
        intArrayOf(Color.BLACK, Color.GREEN)
      )

    /*
    These are assuming the phone screen is facing out with the tilt motor to
    its right when looking at the phone screen.
     */
    // Looking down at pan motor
    private const val PAN_CLOCKWISE =       "0\n"
    private const val PAN_ANTI_CLOCKWISE =  "1\n"

    // Looking at the bottom of the tilt motor (outside of motor)
    private const val TILT_CLOCKWISE =      "2\n"
    private const val TILT_ANTI_CLOCKWISE = "3\n"

    // Buffers to prevent oscillations from overcorrecting (may need additional tuning)
    private const val X_BUFF = 75
    private const val Y_BUFF = 130
  }
}
