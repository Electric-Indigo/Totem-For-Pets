package io.github.electricindigo.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.electricindigo.TotemForPets;
import net.minecraft.client.model.animal.wolf.WolfModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;

public class DogTotemLayer extends RenderLayer<WolfRenderState, WolfModel>
{
    public static final ContextKey<ItemStackRenderState> DOG_TOTEM =
            new ContextKey<>(Identifier.fromNamespaceAndPath(TotemForPets.MODID, "gof_totem"));

    public DogTotemLayer(RenderLayerParent<WolfRenderState, WolfModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack pose, SubmitNodeCollector collector, int lightCoords, WolfRenderState state, float yRot, float xRot)
    {
        ItemStackRenderState totem = state.getRenderData(DOG_TOTEM);
        if (totem == null || totem.isEmpty() || state.isInvisible) return;

        pose.pushPose();

        if (state.isBaby)
        {
            pose.translate(0.0F, 21.5F / 16.0F, -4.5F / 16.0F);
            pose.scale(0.19F, 0.19F, 0.19F);
        }
        else
        {
            pose.translate(0.0F, 18.5F / 16.0F, -6.0F / 16.0F);
            pose.scale(0.25F, 0.25F, 0.25F);
        }

        pose.rotateDegrees(Axis.ZP, 180.0F);

        totem.submit(pose, collector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        pose.popPose();
    }
}
