package dev.aika.artemisia.client.layout;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.aika.artemisia.Constants;
import dev.vfyjxf.taffy.geometry.TaffySize;
import dev.vfyjxf.taffy.style.AvailableSpace;
import dev.vfyjxf.taffy.style.TaffyDimension;
import dev.vfyjxf.taffy.style.TaffyStyle;
import dev.vfyjxf.taffy.tree.NodeId;
import dev.vfyjxf.taffy.tree.TaffyTree;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.Arrays;
import java.util.function.Consumer;

@SuppressWarnings({"UnusedReturnValue", "unused"})
@Accessors(fluent = true, chain = true)
public class EasyLayout {
    @Getter
    protected final TaffyTree tree = new TaffyTree();
    protected final BiMap<NodeId, EasyElement> elements = HashBiMap.create();
    @Getter
    protected final NodeId root;
    @Getter
    @Setter
    protected float layoutX = 0;
    @Getter
    @Setter
    protected float layoutY = 0;

    public EasyLayout() {
        root = newElement().nodeId;
    }

    public EasyElement newElement() {
        final var element = new EasyElement(this);
        elements.put(element.nodeId, element);
        return element;
    }

    public EasyWidget newWidget(AbstractWidget widget) {
        final var element = new EasyWidget(this, widget);
        elements.put(element.nodeId, element);
        return element;
    }

    public EasyWidget newWidget(AbstractWidget widget, TaffyStyle style) {
        final var element = newWidget(widget);
        element.style(style);
        return element;
    }

    public EasyLayout removeElement(EasyElement element) {
        tree.remove(element.nodeId);
        elements.remove(element.nodeId);
        return this;
    }

    public EasyLayout removeElement(NodeId node) {
        tree.remove(node);
        elements.remove(node);
        return this;
    }

    public EasyElement getElement(NodeId node) {
        return elements.get(node);
    }

    public EasyWidget getWidget(AbstractWidget widget) {
        for (EasyElement e : elements.values()) {
            if (e instanceof EasyWidget w && w.widget().equals(widget))
                return w;
        }
        return null;
    }

    public EasyLayout setStyle(NodeId node, TaffyStyle style) {
        tree.setStyle(node, style);
        return this;
    }

    public EasyLayout setStyle(TaffyStyle style) {
        return setStyle(root, style);
    }

    public EasyLayout setChildren(EasyElement parent, EasyElement... children) {
        tree.setChildren(parent.nodeId, Arrays.stream(children).map(EasyElement::nodeId).toArray(NodeId[]::new));
        return this;
    }

    public EasyLayout setChildren(EasyElement... children) {
        tree.setChildren(root, Arrays.stream(children).map(EasyElement::nodeId).toArray(NodeId[]::new));
        return this;
    }

    public EasyLayout addChild(EasyElement child) {
        if (child == null) {
            Constants.LOG.warn("Cannot add child to a null child");
            return this;
        }
        tree.addChild(root, child.nodeId);
        if (child.uiMeasureFunc != null) {
            tree.setMeasureFunc(child.nodeId, (known, space) -> {
                assert child.uiMeasureFunc != null;
                return child.uiMeasureFunc.measure(child, known, space);
            });
        } else if (child.measureFunc != null) {
            tree.setMeasureFunc(child.nodeId, child.measureFunc);
        }
        return this;
    }

    public EasyLayout addChild(EasyElement parent, EasyElement child) {
        tree.addChild(parent.nodeId, child.nodeId);
        return this;
    }

    public EasyLayout addChild(EasyElement parent, EasyElement... children) {
        for (final EasyElement child : children) addChild(parent, child);
        return this;
    }


    public void visitElements(Consumer<EasyElement> elementVisitor) {
        elements.values().forEach(elementVisitor);
    }

    public void visitUiWidgets(Consumer<EasyWidget> widgetVisitor) {
        visitElements((element) -> {
            if (element instanceof EasyWidget w) widgetVisitor.accept(w);
        });
    }

    public void visitWidgets(Consumer<AbstractWidget> widgetVisitor) {
        visitUiWidgets((widget) -> widgetVisitor.accept(widget.widget()));
    }

    public void computeLayout(float width, float height) {
        tree.getStyle(root).size = new TaffySize<>(TaffyDimension.length(width), TaffyDimension.length(height));
        tree.computeLayout(root, new TaffySize<>(AvailableSpace.definite(width), AvailableSpace.definite(height)));
    }

    public void arrangeElements() {
    }
}