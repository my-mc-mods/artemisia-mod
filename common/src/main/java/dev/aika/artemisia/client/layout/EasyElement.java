package dev.aika.artemisia.client.layout;

import dev.vfyjxf.taffy.geometry.FloatPoint;
import dev.vfyjxf.taffy.geometry.FloatSize;
import dev.vfyjxf.taffy.geometry.TaffySize;
import dev.vfyjxf.taffy.style.AvailableSpace;
import dev.vfyjxf.taffy.style.TaffyStyle;
import dev.vfyjxf.taffy.tree.Layout;
import dev.vfyjxf.taffy.tree.NodeId;
import dev.vfyjxf.taffy.util.MeasureFunc;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unused", "UnusedReturnValue"})
@Accessors(fluent = true, chain = true)
public class EasyElement {
    protected final EasyLayout easyLayout;
    @Getter
    protected final NodeId nodeId;
    @Setter
    protected @Nullable MeasureFunc measureFunc;
    @Setter
    protected @Nullable UiMeasureFunc uiMeasureFunc;

    public EasyElement(EasyLayout layout) {
        this.easyLayout = layout;
        this.nodeId = layout.tree.newLeaf(new TaffyStyle());
    }

    public TaffyStyle style() {
        return easyLayout.tree.getStyle(nodeId);
    }

    public EasyElement style(TaffyStyle style) {
        easyLayout.setStyle(nodeId, style);
        return this;
    }

    public FloatPoint getAbsPosition(NodeId node) {
        float x = 0;
        float y = 0;
        NodeId current = node;
        while (current != null) {
            final Layout layout = easyLayout.tree.getLayout(current);
            x += layout.location().x;
            y += layout.location().y;
            current = easyLayout.tree.getParent(current);
        }
        return new FloatPoint(easyLayout.layoutX + x, easyLayout.layoutY + y);
    }

    public FloatPoint getAbsPosition() {
        return getAbsPosition(nodeId);
    }

    public FloatSize getSize(NodeId node) {
        return easyLayout.tree.getLayout(node).size();
    }

    public FloatSize getSize() {
        return getSize(nodeId);
    }

    public EasyElement setParent(NodeId parent) {
        easyLayout.tree.addChild(parent, nodeId);
        return this;
    }

    @FunctionalInterface
    public interface UiMeasureFunc {
        FloatSize measure(
                EasyElement element,
                FloatSize known,
                TaffySize<AvailableSpace> space
        );
    }
}