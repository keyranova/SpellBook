package com.keyranovack.spellbook.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Swords: ImageVector
    get() {
        if (_Swords != null) {
            return _Swords!!
        }
        _Swords = ImageVector.Builder(
            name = "Swords",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(762f, 864f)
                lineTo(645f, 748f)
                lineToRelative(-88f, 88f)
                lineToRelative(-28f, -28f)
                quadToRelative(-23f, -23f, -23f, -57f)
                reflectiveQuadToRelative(23f, -57f)
                lineToRelative(169f, -169f)
                quadToRelative(23f, -23f, 57f, -23f)
                reflectiveQuadToRelative(57f, 23f)
                lineToRelative(28f, 28f)
                lineToRelative(-88f, 88f)
                lineToRelative(116f, 117f)
                quadToRelative(12f, 12f, 12f, 28f)
                reflectiveQuadToRelative(-12f, 28f)
                lineToRelative(-50f, 50f)
                quadToRelative(-12f, 12f, -28f, 12f)
                reflectiveQuadToRelative(-28f, -12f)
                moveToRelative(118f, -628f)
                lineTo(426f, 690f)
                lineToRelative(5f, 4f)
                quadToRelative(23f, 23f, 23f, 57f)
                reflectiveQuadToRelative(-23f, 57f)
                lineToRelative(-28f, 28f)
                lineToRelative(-88f, -88f)
                lineTo(198f, 864f)
                quadToRelative(-12f, 12f, -28f, 12f)
                reflectiveQuadToRelative(-28f, -12f)
                lineToRelative(-50f, -50f)
                quadToRelative(-12f, -12f, -12f, -28f)
                reflectiveQuadToRelative(12f, -28f)
                lineToRelative(116f, -117f)
                lineToRelative(-88f, -88f)
                lineToRelative(28f, -28f)
                quadToRelative(23f, -23f, 57f, -23f)
                reflectiveQuadToRelative(57f, 23f)
                lineToRelative(4f, 5f)
                lineToRelative(454f, -454f)
                horizontalLineToRelative(160f)
                close()
                moveTo(334f, 377f)
                lineToRelative(24f, -23f)
                lineToRelative(23f, -24f)
                lineToRelative(-23f, 24f)
                close()
                moveToRelative(-56f, 57f)
                lineTo(80f, 236f)
                verticalLineToRelative(-160f)
                horizontalLineToRelative(160f)
                lineToRelative(198f, 198f)
                lineToRelative(-57f, 56f)
                lineToRelative(-174f, -174f)
                horizontalLineToRelative(-47f)
                verticalLineToRelative(47f)
                lineToRelative(174f, 174f)
                close()
                moveToRelative(92f, 199f)
                lineToRelative(430f, -430f)
                verticalLineToRelative(-47f)
                horizontalLineToRelative(-47f)
                lineTo(323f, 586f)
                close()
                moveToRelative(0f, 0f)
                lineToRelative(-24f, -23f)
                lineToRelative(-23f, -24f)
                lineToRelative(23f, 24f)
                close()
            }
        }.build()
        return _Swords!!
    }

private var _Swords: ImageVector? = null
