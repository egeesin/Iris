package net.coderbot.iris.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.client.Options;

/**
 * A workaround for when OptiFine has set the maxFps to zero in options.txt
 * 
 * Fun.
 */
@Mixin(Options.class)
public abstract class MixinMaxFpsCrashFix {
	@Redirect(
		method = "load",
		at = @At(value = "INVOKE", target = "Ljava/lang/Integer;parseInt(Ljava/lang/String;)I"),
		slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=maxFps"), to = @At(value = "CONSTANT", args = "stringValue=difficulty")),
		allow = 1
	)
	private int iris$resetFramerateLimit(String string) {
		int original = Integer.parseInt(string);

		if (original == 0) {
			// Return the default value of framerateLimit
			return 120;
		}

		return original;
	}
}
