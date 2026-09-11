package dev.aika.artemisia.client.layout;

import dev.vfyjxf.taffy.geometry.TaffyLine;
import dev.vfyjxf.taffy.geometry.TaffyPoint;
import dev.vfyjxf.taffy.geometry.TaffyRect;
import dev.vfyjxf.taffy.geometry.TaffySize;
import dev.vfyjxf.taffy.style.*;

import java.util.List;

@SuppressWarnings("unused")
public class StyleHelper {
    private StyleHelper() {
    }

    public static StyleBuilder builder(TaffyStyle style) {
        return new StyleBuilder(style);
    }

    public static StyleBuilder builder() {
        return new StyleBuilder();
    }

    @SuppressWarnings("unused")
    public static class StyleBuilder {
        private final TaffyStyle style;

        private StyleBuilder(TaffyStyle style) {
            this.style = style;
        }

        private StyleBuilder() {
            this(new TaffyStyle());
        }

        // === Display and Box Model ===

        public StyleBuilder display(TaffyDisplay display) {
            style.display = display;
            return this;
        }

        public StyleBuilder direction(TaffyDirection direction) {
            style.direction = direction;
            return this;
        }

        public StyleBuilder itemIsTable(boolean itemIsTable) {
            style.itemIsTable = itemIsTable;
            return this;
        }

        public StyleBuilder itemIsReplaced(boolean itemIsReplaced) {
            style.itemIsReplaced = itemIsReplaced;
            return this;
        }

        public StyleBuilder boxSizing(BoxSizing boxSizing) {
            style.boxSizing = boxSizing;
            return this;
        }

        // === Overflow Properties ===

        public StyleBuilder overflow(TaffyPoint<Overflow> overflow) {
            style.overflow = overflow;
            return this;
        }

        public StyleBuilder scrollbarWidth(float scrollbarWidth) {
            style.scrollbarWidth = scrollbarWidth;
            return this;
        }

        // === Position Properties ===

        public StyleBuilder position(TaffyPosition position) {
            style.position = position;
            return this;
        }

        public StyleBuilder inset(TaffyRect<LengthPercentageAuto> inset) {
            style.inset = inset;
            return this;
        }

        public StyleBuilder inset(LengthPercentageAuto left, LengthPercentageAuto right,
                                  LengthPercentageAuto top, LengthPercentageAuto bottom) {
            style.inset = new TaffyRect<>(left, right, top, bottom);
            return this;
        }

        public StyleBuilder inset(float left, float right, float top, float bottom) {
            style.inset = new TaffyRect<>(
                    LengthPercentageAuto.length(left), LengthPercentageAuto.length(right),
                    LengthPercentageAuto.length(top), LengthPercentageAuto.length(bottom)
            );
            return this;
        }

        // === Size Properties ===

        public StyleBuilder size(TaffySize<TaffyDimension> size) {
            style.size = size;
            return this;
        }

        public StyleBuilder size(TaffyDimension width, TaffyDimension height) {
            return size(TaffySize.of(width, height));
        }

        public StyleBuilder size(float width, float height) {
            return size(TaffyDimension.length(width), TaffyDimension.length(height));
        }

        public StyleBuilder minSize(TaffySize<TaffyDimension> size) {
            style.minSize = size;
            return this;
        }

        public StyleBuilder minSize(TaffyDimension width, TaffyDimension height) {
            return minSize(TaffySize.of(width, height));
        }

        public StyleBuilder minSize(float width, float height) {
            return minSize(TaffyDimension.length(width), TaffyDimension.length(height));
        }

        public StyleBuilder maxSize(TaffySize<TaffyDimension> size) {
            style.maxSize = size;
            return this;
        }

        public StyleBuilder maxSize(TaffyDimension width, TaffyDimension height) {
            return maxSize(TaffySize.of(width, height));
        }

        public StyleBuilder maxSize(float width, float height) {
            return maxSize(TaffyDimension.length(width), TaffyDimension.length(height));
        }

        public StyleBuilder aspectRatio(float aspectRatio) {
            style.aspectRatio = aspectRatio;
            return this;
        }

        // === Spacing Properties ===

        public StyleBuilder margin(TaffyRect<LengthPercentageAuto> margin) {
            style.margin = margin;
            return this;
        }

        public StyleBuilder margin(float left, float right, float top, float bottom) {
            style.margin = new TaffyRect<>(
                    LengthPercentageAuto.length(left), LengthPercentageAuto.length(right),
                    LengthPercentageAuto.length(top), LengthPercentageAuto.length(bottom)
            );
            return this;
        }

        public StyleBuilder padding(TaffyRect<LengthPercentage> padding) {
            style.padding = padding;
            return this;
        }

        public StyleBuilder padding(LengthPercentage left, LengthPercentage right,
                                    LengthPercentage top, LengthPercentage bottom) {
            return padding(TaffyRect.of(left, right, top, bottom));
        }

        public StyleBuilder padding(float left, float right, float top, float bottom) {
            return padding(LengthPercentage.length(left), LengthPercentage.length(right),
                    LengthPercentage.length(top), LengthPercentage.length(bottom));
        }

        public StyleBuilder border(TaffyRect<LengthPercentage> border) {
            style.border = border;
            return this;
        }

        public StyleBuilder border(LengthPercentage left, LengthPercentage right,
                                   LengthPercentage top, LengthPercentage bottom) {
            return border(TaffyRect.of(left, right, top, bottom));
        }

        public StyleBuilder border(float left, float right, float top, float bottom) {
            return border(LengthPercentage.length(left), LengthPercentage.length(right),
                    LengthPercentage.length(top), LengthPercentage.length(bottom));
        }

        // === Alignment Properties ===

        public StyleBuilder alignItems(AlignItems alignItems) {
            style.alignItems = alignItems;
            return this;
        }

        public StyleBuilder alignSelf(AlignItems alignSelf) {
            style.alignSelf = alignSelf;
            return this;
        }

        public StyleBuilder justifyItems(AlignItems justifyItems) {
            style.justifyItems = justifyItems;
            return this;
        }

        public StyleBuilder justifySelf(AlignItems justifySelf) {
            style.justifySelf = justifySelf;
            return this;
        }

        public StyleBuilder alignContent(AlignContent alignContent) {
            style.alignContent = alignContent;
            return this;
        }

        public StyleBuilder justifyContent(AlignContent justifyContent) {
            style.justifyContent = justifyContent;
            return this;
        }

        public StyleBuilder gap(TaffySize<LengthPercentage> gap) {
            style.gap = gap;
            return this;
        }

        public StyleBuilder gap(LengthPercentage width, LengthPercentage height) {
            return gap(TaffySize.of(width, height));
        }

        public StyleBuilder gap(float width, float height) {
            return gap(LengthPercentage.length(width), LengthPercentage.length(height));
        }

        // === Block Container Properties ===

        public StyleBuilder textAlign(TextAlign textAlign) {
            style.textAlign = textAlign;
            return this;
        }

        public StyleBuilder flexDirection(FlexDirection flexDirection) {
            style.flexDirection = flexDirection;
            return this;
        }

        public StyleBuilder flexWrap(FlexWrap flexWrap) {
            style.flexWrap = flexWrap;
            return this;
        }

        // === Flexbox Item Properties ===

        public StyleBuilder flex(float flex) {
            style.flex = flex;
            return this;
        }

        public StyleBuilder flexGrow(float flexGrow) {
            style.flexGrow = flexGrow;
            return this;
        }

        public StyleBuilder flexShrink(float flexShrink) {
            style.flexShrink = flexShrink;
            return this;
        }

        public StyleBuilder flexBasis(TaffyDimension flexBasis) {
            style.flexBasis = flexBasis;
            return this;
        }

        // === Grid Container Properties ===

        public StyleBuilder gridTemplateRows(List<TrackSizingFunction> gridTemplateRows) {
            style.gridTemplateRows = gridTemplateRows;
            return this;
        }

        public StyleBuilder gridTemplateColumns(List<TrackSizingFunction> gridTemplateColumns) {
            style.gridTemplateColumns = gridTemplateColumns;
            return this;
        }

        public StyleBuilder gridTemplateRowsWithRepeat(List<GridTemplateComponent> gridTemplateRowsWithRepeat) {
            style.gridTemplateRowsWithRepeat = gridTemplateRowsWithRepeat;
            return this;
        }

        public StyleBuilder gridTemplateColumnsWithRepeat(List<GridTemplateComponent> gridTemplateColumnsWithRepeat) {
            style.gridTemplateColumnsWithRepeat = gridTemplateColumnsWithRepeat;
            return this;
        }

        public StyleBuilder gridTemplateAreas(List<GridTemplateArea> gridTemplateAreas) {
            style.gridTemplateAreas = gridTemplateAreas;
            return this;
        }

        public StyleBuilder gridTemplateColumnNames(List<NamedGridLine> gridTemplateColumnNames) {
            style.gridTemplateColumnNames = gridTemplateColumnNames;
            return this;
        }

        public StyleBuilder gridTemplateRowNames(List<NamedGridLine> gridTemplateRowNames) {
            style.gridTemplateRowNames = gridTemplateRowNames;
            return this;
        }

        public StyleBuilder gridAutoRows(List<TrackSizingFunction> gridAutoRows) {
            style.gridAutoRows = gridAutoRows;
            return this;
        }

        public StyleBuilder gridAutoColumns(List<TrackSizingFunction> gridAutoColumns) {
            style.gridAutoColumns = gridAutoColumns;
            return this;
        }

        public StyleBuilder gridAutoFlow(GridAutoFlow gridAutoFlow) {
            style.gridAutoFlow = gridAutoFlow;
            return this;
        }

        // === Grid Child Properties ===

        public StyleBuilder gridRow(TaffyLine<GridPlacement> gridRow) {
            style.gridRow = gridRow;
            return this;
        }

        public StyleBuilder gridRow(GridPlacement start, GridPlacement end) {
            return gridRow(new TaffyLine<>(start, end));
        }

        public StyleBuilder gridColumn(TaffyLine<GridPlacement> gridColumn) {
            style.gridColumn = gridColumn;
            return this;
        }

        public StyleBuilder gridColumn(GridPlacement start, GridPlacement end) {
            return gridColumn(new TaffyLine<>(start, end));
        }

        public TaffyStyle build() {
            return style;
        }
    }
}