package dev.aika.artemisia.example;

import dev.aika.artemisia.client.layout.EasyLayout;
import dev.aika.artemisia.client.layout.EasyWidget;
import dev.aika.artemisia.client.layout.StyleHelper;
import dev.aika.artemisia.client.screen.EasyScreen;
import dev.aika.artemisia.example.client.components.LinkTextWidget;
import dev.aika.artemisia.example.client.components.UiMultiLineTextWidget;
import dev.aika.artemisia.shadowed.taffy.geometry.FloatSize;
import dev.aika.artemisia.shadowed.taffy.geometry.TaffyRect;
import dev.aika.artemisia.shadowed.taffy.geometry.TaffySize;
import dev.aika.artemisia.shadowed.taffy.style.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.io.IOException;
import java.net.URI;

import static net.minecraft.network.chat.Component.literal;

@Mod(value = ExampleMod.MOD_ID, dist = Dist.CLIENT)
public class ExampleModClient {
    public ExampleModClient(IEventBus ignoredEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (ModContainer ignoredModContainer, Screen parent) -> new DemoScreen(parent)
        );
    }

    public static class DemoScreen extends EasyScreen {
        private final EasyLayout layout = new EasyLayout();

        protected DemoScreen(Screen parent) {
            super(parent, Component.literal("The Project Gutenberg eBook of Alice's Adventures in Wonderland"));
        }

        @Override
        public void init() {
            layout.setStyle(StyleHelper.builder()
                    .display(TaffyDisplay.FLEX)
                    .padding(TaffyRect.all(LengthPercentage.length(8)))
                    .gap(TaffySize.all(LengthPercentage.length(10)))
                    .flexDirection(FlexDirection.COLUMN)
                    .justifyContent(AlignContent.SPACE_BETWEEN)
                    .alignItems(AlignItems.CENTER)
                    .flexWrap(FlexWrap.NO_WRAP)
                    .build());

            final var title = new StringWidget(this.title.copy()
                    .withStyle(style -> style.withBold(true).withColor(CommonColors.YELLOW)),
                    font);
            layout.addChild(layout.newWidget(title)
                    .measureFunc((known, space) -> {
                        title.setMessage(title.getMessage());
                        title.setMaxWidth((int) space.getWidth().getValue() - 40);
                        return FloatSize.of((!Float.isNaN(known.width)) ? known.width : title.getWidth(), font.lineHeight);
                    })
                    .style(StyleHelper.builder()
                            .size(TaffyDimension.auto(), TaffyDimension.length(font.lineHeight))
                            .build()));
            ClassLoader cl = getClass().getClassLoader();
            try (var s = cl.getResourceAsStream("assets/Alice's Adventures in Wonderland.txt")) {
                if (s != null) {
                    var str = new String(s.readAllBytes());
                    layout.addChild(layout.newWidget(new UiMultiLineTextWidget(Component.literal(str), font))
                            .uiMeasureFunc((element, known, space) -> {
                                final var widget = ((UiMultiLineTextWidget) (((EasyWidget) element).widget()));
                                widget.setWidth((int) (space.getWidth().getValue() - 30));
                                return FloatSize.of(widget.getWidth(), known.getHeight());
                            })
                            .style(StyleHelper.builder().flexGrow(1).build())
                    );
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            final var link = new LinkTextWidget(
                    Component.literal("Alice's Adventures in Wonderland"), font, CommonColors.HIGH_CONTRAST_DIAMOND)
                    .setOnPress(LinkTextWidget.confirmLink(this,
                            URI.create("https://www.gutenberg.org/cache/epub/11/pg11-images.html"),
                            false));
            layout.addChild(layout.newWidget(link)
                    .style(StyleHelper.builder()
                            .size(link.getWidth(), font.lineHeight)
                            .build()));

            final var containerStyle = StyleHelper.builder()
                    .display(TaffyDisplay.FLEX)
                    .gap(TaffySize.all(LengthPercentage.length(4)))
                    .flexDirection(FlexDirection.ROW)
                    .justifyContent(AlignContent.CENTER)
                    .build();
            final var childStyle = StyleHelper.builder().size(100, 20).build();
            layout.addChild(layout.newElement().style(containerStyle).setParent(layout.root()),
                    layout.newWidget(Button.builder(literal("Button 1"), this::onClose).build(), childStyle),
                    layout.newWidget(Button.builder(literal("Button 2"), this::onClose).build(), childStyle),
                    layout.newWidget(Button.builder(literal("Button 3"), this::onClose).build(), childStyle));

            layout.visitWidgets(this::addRenderableWidget);
            this.repositionElements();
        }

        @Override
        protected void repositionElements() {
            layout.computeLayout(width, height);
            layout.visitElements(element -> {
                if (element instanceof EasyWidget w) w.applyLayout();
            });
        }
    }
}