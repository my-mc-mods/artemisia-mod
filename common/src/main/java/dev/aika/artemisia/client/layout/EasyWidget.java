package dev.aika.artemisia.client.layout;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.minecraft.client.gui.components.AbstractWidget;

@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class EasyWidget extends EasyElement {
    @Getter
    private final AbstractWidget widget;

    public EasyWidget(EasyLayout tree, AbstractWidget widget) {
        super(tree);
        this.widget = widget;
    }

    public void applyLayout() {
        final var pos = getAbsPosition();
        final var size = getSize();
        widget.setSize((int) size.getWidth(), (int) size.getHeight());
        widget.setPosition((int) pos.x, (int) pos.y);
    }
}