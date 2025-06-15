package app.junsu.junjanote.common.ui.corner

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.min

data class SmoothCornerShape(
    private val cornerRadiusTL: Dp = 0.dp,
    private val smoothnessAsPercentTL: Int = 60,
    private val cornerRadiusTR: Dp = 0.dp,
    private val smoothnessAsPercentTR: Int = 60,
    private val cornerRadiusBR: Dp = 0.dp,
    private val smoothnessAsPercentBR: Int = 60,
    private val cornerRadiusBL: Dp = 0.dp,
    private val smoothnessAsPercentBL: Int = 60,
) : CornerBasedShape(
    topStart = CornerSize(cornerRadiusTL),
    topEnd = CornerSize(cornerRadiusTR),
    bottomEnd = CornerSize(cornerRadiusBR),
    bottomStart = CornerSize(cornerRadiusBL),
) {
    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection,
    ) = when {
        topStart + topEnd + bottomEnd + bottomStart == 0.0f -> {
            Outline.Rectangle(size.toRect())
        }

        smoothnessAsPercentTL + smoothnessAsPercentTR + smoothnessAsPercentBR + smoothnessAsPercentBL == 0 -> {
            Outline.Rounded(
                RoundRect(
                    rect = size.toRect(),
                    topLeft = CornerRadius(topStart),
                    topRight = CornerRadius(topEnd),
                    bottomRight = CornerRadius(bottomEnd),
                    bottomLeft = CornerRadius(bottomStart),
                ),
            )
        }

        else -> {
            Outline.Generic(
                Path().apply {
                    val halfOfShortestSide = min(size.height, size.width) / 2
                    val smoothCornersMap = mutableMapOf<String, SmoothCorner>()

                    var selectedSmoothCorner =
                        smoothCornersMap["$topStart - $smoothnessAsPercentTL"] ?: SmoothCorner(
                            topStart,
                            smoothnessAsPercentTL,
                            halfOfShortestSide,
                        )

                    // Top Left Corner
                    moveTo(
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                    )

                    cubicTo(
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToFurthestSide,
                    )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = 0f,
                            right = selectedSmoothCorner.arcSection.radius * 2,
                            bottom = selectedSmoothCorner.arcSection.radius * 2,
                        ),
                        startAngleRadians = (toRadians(180.0) + selectedSmoothCorner.arcSection.arcStartAngle).toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false,
                    )

                    cubicTo(
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                    )

                    selectedSmoothCorner =
                        smoothCornersMap["$topEnd - $smoothnessAsPercentTR"] ?: SmoothCorner(
                            topEnd,
                            smoothnessAsPercentTR,
                            halfOfShortestSide,
                        )

                    lineTo(
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                    )

                    // Top Right Corner
                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.width - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.width - selectedSmoothCorner.anchorPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                    )

                    arcToRad(
                        rect = Rect(
                            top = 0f,
                            left = size.width - selectedSmoothCorner.arcSection.radius * 2,
                            right = size.width,
                            bottom = selectedSmoothCorner.arcSection.radius * 2,
                        ),
                        startAngleRadians = (toRadians(270.0) + selectedSmoothCorner.arcSection.arcStartAngle).toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false,
                    )

                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                    )

                    selectedSmoothCorner =
                        smoothCornersMap["$bottomEnd - $smoothnessAsPercentBR"] ?: SmoothCorner(
                            bottomEnd,
                            smoothnessAsPercentBR,
                            halfOfShortestSide,
                        )

                    lineTo(
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                    )

                    // Bottom Right Corner
                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.width - selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                        size.height - selectedSmoothCorner.anchorPoint2.distanceToFurthestSide,
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - selectedSmoothCorner.arcSection.radius * 2,
                            left = size.width - selectedSmoothCorner.arcSection.radius * 2,
                            right = size.width,
                            bottom = size.height,
                        ),
                        startAngleRadians = (toRadians(0.0) + selectedSmoothCorner.arcSection.arcStartAngle).toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false,
                    )

                    cubicTo(
                        size.width - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.width - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.width - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                    )

                    selectedSmoothCorner =
                        smoothCornersMap["$bottomStart - $smoothnessAsPercentBL"] ?: SmoothCorner(
                            bottomStart,
                            smoothnessAsPercentBL,
                            halfOfShortestSide,
                        )

                    lineTo(
                        selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                    )

                    // Bottom Left Corner
                    cubicTo(
                        selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        selectedSmoothCorner.anchorPoint2.distanceToFurthestSide,
                        size.height - selectedSmoothCorner.anchorPoint2.distanceToClosestSide,
                    )

                    arcToRad(
                        rect = Rect(
                            top = size.height - selectedSmoothCorner.arcSection.radius * 2,
                            left = 0f,
                            right = selectedSmoothCorner.arcSection.radius * 2,
                            bottom = size.height,
                        ),
                        startAngleRadians = (toRadians(90.0) + selectedSmoothCorner.arcSection.arcStartAngle).toFloat(),
                        sweepAngleRadians = selectedSmoothCorner.arcSection.arcSweepAngle,
                        forceMoveTo = false,
                    )

                    cubicTo(
                        selectedSmoothCorner.controlPoint2.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint2.distanceToFurthestSide,
                        selectedSmoothCorner.controlPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.controlPoint1.distanceToFurthestSide,
                        selectedSmoothCorner.anchorPoint1.distanceToClosestSide,
                        size.height - selectedSmoothCorner.anchorPoint1.distanceToFurthestSide,
                    )

                    close()
                },
            )
        }
    }

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize,
    ) = SmoothCornerShape(
        cornerRadiusTL,
        smoothnessAsPercentTL,
        cornerRadiusTR,
        smoothnessAsPercentTR,
        cornerRadiusBR,
        smoothnessAsPercentBR,
        cornerRadiusBL,
        smoothnessAsPercentBL,
    )
}

fun SmoothCornerShape(
    all: Dp,
    smoothnessAsPercent: Int = 60,
) = SmoothCornerShape(
    cornerRadiusTL = all,
    smoothnessAsPercentTL = smoothnessAsPercent,
    cornerRadiusTR = all,
    smoothnessAsPercentTR = smoothnessAsPercent,
    cornerRadiusBR = all,
    smoothnessAsPercentBR = smoothnessAsPercent,
    cornerRadiusBL = all,
    smoothnessAsPercentBL = smoothnessAsPercent,
)


fun SmoothCornerShape(
    topLeft: Dp = 0.dp,
    topRight: Dp = 0.dp,
    bottomRight: Dp = 0.dp,
    bottomLeft: Dp = 0.dp,
    smoothnessAsPercent: Int = 60,
) = SmoothCornerShape(
    cornerRadiusTL = topLeft,
    smoothnessAsPercentTL = smoothnessAsPercent,
    cornerRadiusTR = topRight,
    smoothnessAsPercentTR = smoothnessAsPercent,
    cornerRadiusBR = bottomRight,
    smoothnessAsPercentBR = smoothnessAsPercent,
    cornerRadiusBL = bottomLeft,
    smoothnessAsPercentBL = smoothnessAsPercent,
)


