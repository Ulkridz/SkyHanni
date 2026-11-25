//~ if >= 26.3 '150' -> '330'
#version 150
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

uniform sampler2D Sampler0;

//~ if >= 26.3 'in vec2 texCoord;' -> 'layout(location = 0) in vec2 texCoord;'
in vec2 texCoord;
//~ if >= 26.3 'in vec4 vertexColor;' -> 'layout(location = 1) in vec4 vertexColor;'
in vec4 vertexColor;

//~ if >= 26.3 'out vec4 fragColor;' -> 'layout(location = 0) out vec4 fragColor;'
out vec4 fragColor;

void main() {
    fragColor = texture(Sampler0, texCoord) * vertexColor;
}
