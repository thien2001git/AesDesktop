package Icon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun rememberEditDocument(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "edit_document",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9.542f, 36.375f)
                quadToRelative(-1.042f, 0f, -1.834f, -0.771f)
                quadToRelative(-0.791f, -0.771f, -0.791f, -1.854f)
                verticalLineTo(6.25f)
                quadToRelative(0f, -1.083f, 0.791f, -1.854f)
                quadToRelative(0.792f, -0.771f, 1.834f, -0.771f)
                horizontalLineTo(22.25f)
                quadToRelative(0.542f, 0f, 1.042f, 0.208f)
                quadToRelative(0.5f, 0.209f, 0.875f, 0.584f)
                lineToRelative(8.125f, 8.125f)
                quadToRelative(0.375f, 0.375f, 0.583f, 0.854f)
                quadToRelative(0.208f, 0.479f, 0.208f, 1.021f)
                verticalLineToRelative(5.333f)
                horizontalLineToRelative(-2.625f)
                verticalLineToRelative(-4.792f)
                horizontalLineToRelative(-7.375f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.395f)
                quadToRelative(-0.375f, -0.396f, -0.375f, -0.938f)
                verticalLineTo(6.25f)
                horizontalLineTo(9.542f)
                verticalLineToRelative(27.5f)
                horizontalLineToRelative(11.666f)
                verticalLineToRelative(2.625f)
                close()
                moveToRelative(0f, -2.625f)
                verticalLineTo(6.25f)
                verticalLineToRelative(27.5f)
                close()
                moveTo(31f, 24.708f)
                lineTo(32.25f, 26f)
                lineToRelative(-6.458f, 6.458f)
                verticalLineToRelative(2.125f)
                horizontalLineToRelative(2.083f)
                lineToRelative(6.542f, -6.5f)
                lineToRelative(1.25f, 1.25f)
                lineTo(29f, 36f)
                quadToRelative(-0.208f, 0.208f, -0.438f, 0.292f)
                quadToRelative(-0.229f, 0.083f, -0.479f, 0.083f)
                horizontalLineToRelative(-3.458f)
                quadToRelative(-0.25f, 0f, -0.458f, -0.187f)
                quadToRelative(-0.209f, -0.188f, -0.209f, -0.48f)
                verticalLineToRelative(-3.416f)
                quadToRelative(0f, -0.25f, 0.084f, -0.48f)
                quadToRelative(0.083f, -0.229f, 0.333f, -0.437f)
                close()
                moveToRelative(4.667f, 4.625f)
                lineTo(31f, 24.708f)
                lineToRelative(2.458f, -2.416f)
                quadToRelative(0.375f, -0.417f, 0.917f, -0.417f)
                reflectiveQuadToRelative(0.917f, 0.375f)
                lineToRelative(2.791f, 2.792f)
                quadToRelative(0.375f, 0.416f, 0.375f, 0.937f)
                quadToRelative(0f, 0.521f, -0.375f, 0.938f)
                close()
            }
        }.build()
    }
}