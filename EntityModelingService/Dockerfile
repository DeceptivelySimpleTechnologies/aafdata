#NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

FROM nginx:1.27.3
MAINTAINER deceptivelysimpletechnologies.com
LABEL description="AAF Data: Entity Modeling Service (EMS)"
LABEL copyright="Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details."
COPY nginx.conf /etc/nginx/conf.d/default.conf
#NOTE: No ENTRYPOINT or CMD is specified for this image since we will use the underlying ENTRYPOINT and CMD provided by the base NGINX image.
